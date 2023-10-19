package delta.games.lotro.gui.configuration;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.JPanel;

import delta.common.ui.swing.Dialog;
import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.windows.DefaultFormDialogController;
import delta.common.ui.swing.windows.WindowController;
import delta.games.lotro.config.path.EnumApplicationPath;
import delta.games.lotro.dat.archive.DatChecks;
import delta.games.lotro.utils.cfg.ApplicationConfiguration;
import delta.games.lotro.utils.cfg.ModificationStatus;
import delta.games.lotro.utils.cfg.ModificationStatusEnum;
import delta.games.lotro.utils.plugin.LotroPluginConfiguration;

/**
 * Controller for the "configuration" dialog.
 * @author DAM
 */
public class ConfigurationDialogController extends DefaultFormDialogController<ApplicationConfiguration>
{
  // Controllers
    // Application paths
  private AppPathConfigurationPanelController _appPathController;
    // Lotro paths
  private AppPathConfigurationPanelController _lotroPathController;
    // Labels
  private LabelsConfigurationPanelController _labels;
    // Gui pattern
  private GuiPatternConfigurationPanelController _guiPattern;
    // Plugins
  private PluginConfigurationPanelController _pluginController;
  
  /**
   * Constructor.
   * @param parentController Parent controller.
   * @param configuration Configuration to edit.
   */
  public ConfigurationDialogController(WindowController parentController, ApplicationConfiguration configuration)
  {
    super(parentController,configuration);
  }

  @Override
  protected Dialog build()
  {
    Dialog dialog=super.build();
    dialog.setTitle("Configuration..."); // I18n
    dialog.setResizable(true);
    return dialog;
  }

  @Override
  protected JPanel buildFormPanel()
  {
    // Build components
    _appPathController=new AppPathConfigurationPanelController(
        this,
        EnumApplicationPath.APPLICATION_PATH_CONFIGURATION
    );
    _lotroPathController=new AppPathConfigurationPanelController(
        this,
        EnumApplicationPath.LOTRO_APPLICATION_PATH_CONFIGURATION
    );
    _labels=new LabelsConfigurationPanelController();
    _guiPattern=new GuiPatternConfigurationPanelController();
    _pluginController=new PluginConfigurationPanelController();

    // Init
    init();
    // Layout
    JPanel ret=GuiFactory.buildPanel(new GridBagLayout());
    GridBagConstraints c=new GridBagConstraints(0,0,1,1,1.0,0.0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(5,5,0,5),0,0);
    ret.add(_appPathController.getPanel(),c);
    c=new GridBagConstraints(0,2,1,1,1.0,0.0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(5,5,5,5),0,0);
    ret.add(_lotroPathController.getPanel(),c);
    c=new GridBagConstraints(0,3,1,1,1.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
    ret.add(_labels.getPanel(),c);
    c=new GridBagConstraints(0,4,1,1,1.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
    ret.add(_guiPattern.getPanel(),c);
    c=new GridBagConstraints(0,5,1,1,1.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
    ret.add(_pluginController.getPanel(),c);
    return ret;
  }

  private void init()
  {
    // Application paths
    _appPathController.setConfig(_data.getPathConfigurationMap(EnumApplicationPath.APPLICATION_PATH_CONFIGURATION));
    // Lotro paths
    _lotroPathController.setConfig(_data.getPathConfigurationMap(EnumApplicationPath.LOTRO_APPLICATION_PATH_CONFIGURATION));
    // Labels
    _labels.setConfig(_data.getLabelsConfiguration());
    // Gui pattern
    _guiPattern.setConfig(_data.getGuiPatternConfiguration());
    // Plugins
    _pluginController.setConfig((LotroPluginConfiguration)_data.getPluginConfiguration());
  }

  @Override
  protected void okImpl()
  {
    ModificationStatus modificationStatus=new ModificationStatus();
    
    // Application paths
    //_appPathController.saveTo(_data.getAppPathConfiguration());
    // Lotro paths
    //_lotroPathController.saveTo(_data.getLotroPathConfiguration());
    /*if (!Objects.equals(dataPath,_data.getDataConfiguration().getRootPath())) {
      modificationStatus.set(ModificationStatusEnum.MODIFICATION_STATUS_DATA_PATH);
    }*/  
    // Labels
    _labels.saveTo(_data.getLabelsConfiguration());
    // Gui pattern
    _guiPattern.saveTo(_data.getGuiPatternConfiguration(), modificationStatus);
    // Plugins
    _pluginController.saveTo((LotroPluginConfiguration)_data.getPluginConfiguration());
    _data.saveConfiguration();
    if (modificationStatus.hasModifications()) {
      warnOnModificationChange(modificationStatus);
    }

    _data.configurationUpdated(modificationStatus);
  }

  private void warnOnModificationChange(ModificationStatus modificationStatus)
  {
    String title="Restart application"; // I18n
    String message="Unknown changes have occured.\nPlease restart the application so that it takes full effect!"; // I18n
    if (modificationStatus.contains(ModificationStatusEnum.MODIFICATION_STATUS_DATA_PATH)) {
      message="User data path has changed.\nPlease restart the application so that it takes effect!"; // I18n
    } else if (modificationStatus.contains(ModificationStatusEnum.MODIFICATION_STATUS_SKIN)) {
      message="Skin has changed.\nPlease restart the application so that it takes full effect!"; // I18n
    }

    GuiFactory.showInformationDialog(getDialog(),message,title);
  }

  @Override
  protected boolean checkInput()
  {
    String errorMsg=checkData();
    if (errorMsg==null)
    {
      return true;
    }
    showErrorMessage(errorMsg);
    return false;
  }

  private String checkData()
  {
    String errorMsg=checkDatConfiguration();
    if (errorMsg!=null)
    {
      return errorMsg;
    }
    return checkDataConfiguration();
  }

  private String checkDatConfiguration()
  {
    String errorMsg=null;
    // DAT
    String datPathStr=_lotroPathController.getAppPath();
    File datPath=new File(datPathStr);
    boolean ok=DatChecks.checkDatPath(datPath);
    if (!ok)
    {
      errorMsg="Invalid LOTRO installation directory!"; // I18n
    }
    return errorMsg;
  }

  private String checkDataConfiguration()
  {
    return null;
  }

  private void showErrorMessage(String errorMsg)
  {
    String title="Configuration"; // I18n
    Dialog dialog=getDialog();
    GuiFactory.showErrorDialog(dialog,errorMsg,title);
  }

  /**
   * Release all managed resources.
   */
  @Override
  public void dispose()
  {
    super.dispose();
    if (_pluginController!=null)
    {
      _pluginController.dispose();
      _pluginController=null;
    }
    if (_guiPattern!=null)
    {
      _guiPattern.dispose();
      _guiPattern=null;
    }
    if (_labels!=null)
    {
      _labels.dispose();
      _labels=null;
    }
    if (_lotroPathController!=null)
    {
      _lotroPathController.dispose();
      _lotroPathController=null;
    }
    if (_appPathController!=null)
    {
      _appPathController.dispose();
      _appPathController=null;
    }
  }
}
