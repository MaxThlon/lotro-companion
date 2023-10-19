package delta.games.lotro.utils.gui;

import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.swing.SwingUtilities;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.log4j.Logger;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.pattern.GuiPattern;
import delta.common.ui.swing.pattern.GuiPatternManager;
import delta.games.lotro.client.skin.io.xml.SkinXMLParser;
import delta.games.lotro.ui.pattern.LotroGuiPattern;
import delta.games.lotro.utils.cfg.ApplicationConfiguration;
import delta.games.lotro.utils.cfg.ConfigurationListener;
import delta.games.lotro.utils.cfg.ModificationStatus;
import delta.games.lotro.utils.cfg.ModificationStatusEnum;

/**
 * GuiPatternManager
 * @author MaxThlon
 */
public class LotroGuiPatternManager extends GuiPatternManager implements ConfigurationListener {
  private static final Logger LOGGER=Logger.getLogger(LotroGuiPatternManager.class);

  static {
    /**
     * Add {@code LotroGuiPattern} to parent GuiPatternManager gui pattern list
     */
    _guiPatternList.add(new ImmutableTriple<String, Supplier<GuiPattern>, Supplier<List<String>>>(
        "Lotro", LotroGuiPattern::new, null
    ));
  }

  private static LotroGuiPatternManager _instance=null;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static LotroGuiPatternManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new LotroGuiPatternManager();
    }
    return _instance;
  }

  /**
   * Constructor
   */
  protected LotroGuiPatternManager() {
    super(ApplicationConfiguration.getInstance().getGuiPatternConfiguration());
    
    ApplicationConfiguration applicationCfg=ApplicationConfiguration.getInstance();
    applicationCfg.getListeners().addListener(this);
  }

  /**
   * @return a LotroGuiPatternConfiguration.
   */
  public LotroGuiPatternConfiguration getLotroGuiPatternConfiguration() {
    return (LotroGuiPatternConfiguration)_guiPatternConfiguration;
  }
  
  /**
   * find path with matcher.
   * @param path 
   * @param matcher 
   * @return Stream<Path>.
   * @throws IOException 
   */
  protected Stream<Path> findParallel(Path path, PathMatcher matcher) throws IOException {
    return Files.walk(path).filter(matcher::matches).parallel();
  }

  @Override
  public Stream<ImmutablePair<String, Path>> loadSkinList() {
    Stream<ImmutablePair<String, Path>> skinList;
    switch (getLotroGuiPatternConfiguration().getSkinAppPath())
    {
      case LOTRO_APPLICATION_PATH_CONFIGURATION: {
        SkinXMLParser skinXMLParser=new SkinXMLParser();
        try {
          skinList=findParallel(
              _guiPatternConfiguration.getSkinsPath(),
              FileSystems.getDefault().getPathMatcher("glob:**" + LotroGuiPatternConfiguration.LOTRO_SKIN_DEFINITION_FILENAME)
          ).map((skinPath) -> new ImmutablePair<String, Path>(skinXMLParser.parseSkinName(skinPath.toFile()), skinPath.getParent()));
        }
        catch (IOException e) {
          LOGGER.info(e);
          skinList=Stream.empty();
        }
        
        break;
      }
      case APPLICATION_PATH_CONFIGURATION:
      default:
        skinList=super.loadSkinList();
      break;
    }
    return skinList;
  }

  @Override
  public void loadSkin() {
    File skinDefinitionFile=_guiPatternConfiguration.getSkinDefinitionFile();

    if (skinDefinitionFile != null) {
      _skin=new SkinXMLParser().parseSkinData(skinDefinitionFile);
    }
  }

  @Override
  public void configurationUpdated(ApplicationConfiguration newConfiguration,
                                   ModificationStatus modificationStatus) {
    if (modificationStatus.contains(ModificationStatusEnum.MODIFICATION_STATUS_GUI_PATTERN_THEME)) {
      initialize();
    }
    if (modificationStatus.contains(ModificationStatusEnum.MODIFICATION_STATUS_SKIN)) {
      loadSkin();
    }
    
    if (modificationStatus.contains(ModificationStatusEnum.MODIFICATION_STATUS_GUI_PATTERN_THEME,
                                    ModificationStatusEnum.MODIFICATION_STATUS_SKIN)) {
      GuiFactory.reloadCache();
    }
    
    if (modificationStatus.contains(ModificationStatusEnum.MODIFICATION_STATUS_GUI_PATTERN_THEME)) {
      for (Window window : Window.getWindows()) {
        SwingUtilities.updateComponentTreeUI(window);
      }
    } else if (modificationStatus.contains(ModificationStatusEnum.MODIFICATION_STATUS_SKIN)) {
      for (Window window : Window.getWindows()) {
        window.repaint();
      }
    }
  }
}
