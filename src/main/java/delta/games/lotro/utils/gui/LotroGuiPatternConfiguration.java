package delta.games.lotro.utils.gui;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import delta.common.ui.swing.pattern.GuiPatternConfiguration;
import delta.common.utils.misc.TypedProperties;
import delta.games.lotro.config.path.EnumApplicationPath;
import delta.games.lotro.utils.cfg.ApplicationConfiguration;

/**
 * @author MaxThlon
 */
public class LotroGuiPatternConfiguration extends GuiPatternConfiguration
{
  private static final String LOTRO_GUI_PATTERN_USER_PATH_INSTANCE="userPathInstance";

  private static Path LOTRO_SKINS_PATH=Paths.get("UI", "skins");
  /**
   * LOTRO_SKIN_DEFINITION_FILENAME
   */
  public static String LOTRO_SKIN_DEFINITION_FILENAME="SkinDefinition.xml";

  private ApplicationConfiguration _applicationCfg;
  private EnumApplicationPath _skinAppPath;

  /**
   * @param applicationCfg .
   */
  public LotroGuiPatternConfiguration(ApplicationConfiguration applicationCfg)
  {
    super(applicationCfg.getAppPathConfiguration());
    _applicationCfg=applicationCfg;
    _skinAppPath=EnumApplicationPath.APPLICATION_PATH_CONFIGURATION;
  }
  
  /**
   * Get the UserPathInstance.
   * @return a UserPathInstance.
   */
  public EnumApplicationPath getSkinAppPath()
  {
    return _skinAppPath;
  }
  
  /**
   * Set the skinAppPath.
   * @param skinAppPath .
   */
  public void setSkinAppPath(EnumApplicationPath skinAppPath)
  {
    _skinAppPath=skinAppPath;
  }
  
  /**
   * Get the user data path for skins.
   * @return a directory path.
   */
  public Path getSkinsPath()
  {
    Path skinsPath;
    switch (_skinAppPath)
    {
      case LOTRO_APPLICATION_PATH_CONFIGURATION:
        skinsPath=_applicationCfg.getPathConfigurationMap(EnumApplicationPath.LOTRO_APPLICATION_PATH_CONFIGURATION)
                                 .getUserPath().resolve(LOTRO_SKINS_PATH);
        break;

      case APPLICATION_PATH_CONFIGURATION:
      default:
        skinsPath=super.getSkinsPath();
    }
    return skinsPath;
  }

  @Override
  public Path getSkinPath()
  {
    Path skinPath;
    switch (_skinAppPath)
    {
      case LOTRO_APPLICATION_PATH_CONFIGURATION: {
        Path skinsPath=getSkinsPath();
        skinPath=((skinsPath != null) && (_skinPath != null))?
            skinsPath.resolve(_skinPath):null;
        break;
      }
      case APPLICATION_PATH_CONFIGURATION:
      default:
        skinPath=super.getSkinPath();
    }
    return skinPath;
  }

  @Override
  public File getSkinDefinitionFile()
  {
    File skinDefinitionFilename=null;

    switch (_skinAppPath)
    {
      case LOTRO_APPLICATION_PATH_CONFIGURATION: {
        Path skinPath=getSkinPath();
        if(skinPath != null) {
          skinDefinitionFilename=getSkinPath().resolve(LOTRO_SKIN_DEFINITION_FILENAME).toFile();
        }
        break;
      }
      case APPLICATION_PATH_CONFIGURATION:
      default:
        skinDefinitionFilename=super.getSkinDefinitionFile();
    }
    return skinDefinitionFilename;
  }

  @Override
  protected void FromProperties(TypedProperties props) {
    super.FromProperties(props);

    String userPathInstanceString=props.getStringProperty(LOTRO_GUI_PATTERN_USER_PATH_INSTANCE, _skinAppPath.name());
    setSkinAppPath(EnumApplicationPath.valueOf(userPathInstanceString));
  }
  
  @Override
  protected void save(TypedProperties props)
  {
    super.save(props);
    props.setStringProperty(LOTRO_GUI_PATTERN_USER_PATH_INSTANCE, _skinAppPath.name());
  }
}
