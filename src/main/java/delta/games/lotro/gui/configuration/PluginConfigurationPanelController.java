package delta.games.lotro.gui.configuration;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Objects;

import javax.swing.JPanel;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.combobox.ComboBoxController;
import delta.common.ui.swing.panels.AbstractPanelController;
import delta.games.lotro.config.path.EnumApplicationPath;
import delta.games.lotro.utils.plugin.LotroPluginConfiguration;

/**
 * Controller for a panel to edit the client user data configuration.
 * @author MaxThlon
 */
public class PluginConfigurationPanelController extends AbstractPanelController
{
  //LotroGuiPatternManager _guiPatternManager;
  // App path
  private ComboBoxController<EnumApplicationPath> _appPathCB;
  
  /**
   * Constructor.
   */
  public PluginConfigurationPanelController()
  {
    super();
    
    //_guiPatternManager=LotroGuiPatternManager.getInstance();
  
    _appPathCB=buildPluginAppPathComboBox();

    setPanel(buildPanel());
  }

  private JPanel buildPanel()
  {
    JPanel panel=GuiFactory.buildPanel(new GridBagLayout());
    int y=0;
    
    // guiPatternFactory
    GridBagConstraints c=new GridBagConstraints(0,y,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
    c=new GridBagConstraints(0,y,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
    panel.add(GuiFactory.buildLabel("Application path:"),c); // I18n
    c=new GridBagConstraints(1,y,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(5,5,5,5),0,0);
    panel.add(_appPathCB.getComboBox(),c);
    ++y;

    panel.setBorder(GuiFactory.buildTitledBorder("Plugins")); // I18n
    return panel;
  }

  /**
   * Set the configuration to show.
   * @param cfg Configuration to show.
   */
  public void setConfig(LotroPluginConfiguration cfg)
  {
    // App path
    _appPathCB.selectItem(cfg.getPluginAppPath());
  }

  /**
   * Save the configuration to the given storage.
   * @param cfg Storage to use.
   */
  public void saveTo(LotroPluginConfiguration cfg)
  {
    EnumApplicationPath newPluginApplicationPath=_appPathCB.getSelectedItem();
    if (!Objects.equals(cfg.getPluginAppPath(), newPluginApplicationPath)) {
      cfg.setPluginAppPath(newPluginApplicationPath);
    }
  }
  
  private ComboBoxController<EnumApplicationPath> buildPluginAppPathComboBox()
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
    if (_appPathCB!=null)
    {
      _appPathCB.dispose();
      _appPathCB=null;
    }
  }
}
