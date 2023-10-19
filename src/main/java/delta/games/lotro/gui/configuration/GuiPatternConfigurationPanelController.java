package delta.games.lotro.gui.configuration;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.swing.JPanel;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.combobox.ComboBoxController;
import delta.common.ui.swing.panels.AbstractPanelController;
import delta.common.ui.swing.theme.Theme;
import delta.games.lotro.config.path.EnumApplicationPath;
import delta.games.lotro.utils.cfg.ModificationStatus;
import delta.games.lotro.utils.cfg.ModificationStatusEnum;
import delta.games.lotro.utils.gui.LotroGuiPatternConfiguration;
import delta.games.lotro.utils.gui.LotroGuiPatternManager;

/**
 * Controller for a panel to edit the client user data configuration.
 * @author MaxThlon
 */
public class GuiPatternConfigurationPanelController extends AbstractPanelController
{
  LotroGuiPatternManager _guiPatternManager;
  
  // GuiPattern
  private ComboBoxController<String> _guiPatternFactoryCB;
  private List<Theme> _themes;
  private ComboBoxController<Theme> _themesCB;
  // Skin
  private Map<String, Path> _skins;
  private ComboBoxController<EnumApplicationPath> _skinAppPathCB;
  private ComboBoxController<String> _skinsCB;
  
  /**
   * Constructor.
   */
  public GuiPatternConfigurationPanelController()
  {
    super();
    
    _guiPatternManager=LotroGuiPatternManager.getInstance();
  
    _guiPatternFactoryCB=buildGuiPatternFactoriesComboBox(_guiPatternManager.getGuiPatternFactories());

    _themes=_guiPatternManager.getThemes();
    _themesCB=buildThemesComboBox(_themes);

    _skins=_guiPatternManager.getSkins();
    _skinAppPathCB=buildSkinAppPathComboBox();
    _skinsCB=buildSkinsComboBox(_skins);

    setPanel(buildPanel());
  }

  private JPanel buildPanel()
  {
    JPanel panel=GuiFactory.buildPanel(new GridBagLayout());
    int y=0;
    
    // guiPatternFactory
    GridBagConstraints c=new GridBagConstraints(0,y,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
    c=new GridBagConstraints(0,y,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
    panel.add(GuiFactory.buildLabel("Mode:"),c); // I18n
    c=new GridBagConstraints(1,y,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(5,5,5,5),0,0);
    panel.add(_guiPatternFactoryCB.getComboBox(),c);
    ++y;
    
    // guiPattern
    c=new GridBagConstraints(0,y,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
    c=new GridBagConstraints(0,y,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
    panel.add(GuiFactory.buildLabel("Theme:"),c); // I18n
    c=new GridBagConstraints(1,y,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(5,5,5,5),0,0);
    panel.add(_themesCB.getComboBox(),c);
    ++y;

    // Skin
    JPanel skinPanel=GuiFactory.buildPanel(new GridBagLayout());
    c=new GridBagConstraints(0,0,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
    skinPanel.add(GuiFactory.buildLabel("Application path:"),c); // I18n
    c=new GridBagConstraints(1,0,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(5,5,5,5),0,0);
    skinPanel.add(_skinAppPathCB.getComboBox(),c);
    c=new GridBagConstraints(0,1,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
    skinPanel.add(GuiFactory.buildLabel("Skin:"),c); // I18n
    c=new GridBagConstraints(1,1,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(5,5,5,5),0,0);
    skinPanel.add(_skinsCB.getComboBox(),c);
    c=new GridBagConstraints(0,y,2,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
    skinPanel.setBorder(GuiFactory.buildTitledBorder("Skin")); // I18n
    panel.add(skinPanel, c);

    panel.setBorder(GuiFactory.buildTitledBorder("User interface")); // I18n
    return panel;
  }

  /**
   * Set the configuration to show.
   * @param cfg Configuration to show.
   * @param manager .
   */
  public void setConfig(LotroGuiPatternConfiguration cfg)
  {
    // guiPatternFactory
    _guiPatternFactoryCB.selectItem(cfg.getGuiPatternFactoryName());
    
    // theme
    Theme currentTheme=cfg.getTheme();
    _themesCB.selectItem(
        _themes.stream()
          .filter(theme -> theme.equals(currentTheme))
          .findFirst()
          .orElse(null)
    );

    // Skin
    _skinAppPathCB.selectItem(cfg.getSkinAppPath());
    _skinsCB.selectItem(_guiPatternManager.getSkinName());
  }

  /**
   * Save the configuration to the given storage.
   * @param cfg Storage {@code ClientUserDataConfiguration} to use.
   * @param modificationStatus {@code ModificationStatus}
   */
  public void saveTo(LotroGuiPatternConfiguration cfg,
                     ModificationStatus modificationStatus)
  {
    String newGuiPatternFactoryName=_guiPatternFactoryCB.getSelectedItem();
    if (!Objects.equals(cfg.getGuiPatternFactoryName(), newGuiPatternFactoryName)) {
      modificationStatus.set(ModificationStatusEnum.MODIFICATION_STATUS_GUI_PATTERN_FACTORY);
      cfg.setGuiPatternFactoryName(newGuiPatternFactoryName);
    }

    Theme newTheme=_themesCB.getSelectedItem();
    if (!newTheme.equals(cfg.getTheme())) {
      modificationStatus.set(ModificationStatusEnum.MODIFICATION_STATUS_GUI_PATTERN_THEME);
      cfg.setTheme(newTheme);
    }
    
    EnumApplicationPath newSkinApplicationPath=_skinAppPathCB.getSelectedItem();
    if (!Objects.equals(cfg.getSkinAppPath(), newSkinApplicationPath)) {
      modificationStatus.set(ModificationStatusEnum.MODIFICATION_STATUS_SKIN);
      cfg.setSkinAppPath(newSkinApplicationPath);
    }
    Path newSkinPath=_skins.getOrDefault(_skinsCB.getSelectedItem(), null);
    if (!Objects.equals(cfg.getSkinPath(), newSkinPath)) {
      modificationStatus.set(ModificationStatusEnum.MODIFICATION_STATUS_SKIN);
      cfg.setSkinPath(newSkinPath);
    }
  }

  private ComboBoxController<String> buildGuiPatternFactoriesComboBox(Set<String> guiPatternFactories)
  {
    ComboBoxController<String> ret=new ComboBoxController<String>();
    guiPatternFactories.forEach(guiPatternFactoryName-> ret.addItem(guiPatternFactoryName, guiPatternFactoryName));
    return ret;
  }
  
  private ComboBoxController<Theme>
  buildThemesComboBox(List<Theme> themes)
  {
    ComboBoxController<Theme> ret=
        new ComboBoxController<Theme>();
    themes.forEach(theme -> ret.addItem(theme, theme.formatGuiPatternNameAndThemeName()));
    return ret;
  }
  
  private ComboBoxController<String> buildSkinsComboBox(Map<String, Path> skins)
  {
    ComboBoxController<String> ret=new ComboBoxController<String>();
    skins.entrySet().forEach(ite-> ret.addItem(ite.getKey(), ite.getKey()));
    return ret;
  }
  
  private ComboBoxController<EnumApplicationPath> buildSkinAppPathComboBox()
  {
    ComboBoxController<EnumApplicationPath> ret=new ComboBoxController<EnumApplicationPath>();
    ret.addItem(
        EnumApplicationPath.APPLICATION_PATH_CONFIGURATION,
        EnumApplicationPath.APPLICATION_PATH_CONFIGURATION.toString()
    );
    ret.addItem(
        EnumApplicationPath.LOTRO_APPLICATION_PATH_CONFIGURATION,
        EnumApplicationPath.LOTRO_APPLICATION_PATH_CONFIGURATION.toString()
    );
    return ret;
  }

  @Override
  public void dispose()
  {
    super.dispose();
    if (_skinAppPathCB!=null)
    {
      _skinAppPathCB.dispose();
      _skinAppPathCB=null;
    }
    if (_skinsCB!=null)
    {
      _skinsCB.dispose();
      _skinsCB=null;
    }
    _skins=null;
    if (_themesCB!=null)
    {
      _themesCB.dispose();
      _themesCB=null;
    }
    if (_guiPatternFactoryCB!=null)
    {
      _guiPatternFactoryCB.dispose();
      _guiPatternFactoryCB=null;
    }
  }
}
