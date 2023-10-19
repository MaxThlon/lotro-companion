package delta.games.lotro.gui.configuration;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.area.AreaController;
import delta.common.ui.swing.panels.AbstractPanelController;
import delta.common.utils.application.config.path.ApplicationPathConfiguration;
import delta.games.lotro.config.path.CompanionApplicationPathConfiguration;
import delta.games.lotro.config.path.EnumApplicationPath;
import delta.games.lotro.utils.cfg.ModificationStatus;
import delta.games.lotro.utils.gui.filechooser.FileChooserController;

/**
 * Controller for a panel to edit the client user data configuration.
 * @author MaxThlon
 */
public class AppPathConfigurationPanelController extends AbstractPanelController
{
  private static final int PATH_SIZE=50;

  // Config
  EnumApplicationPath _appPathInstance;
  // Data
  String _label;
  // Application paths
  private JTextField _appPath;
  private JTextField _userPath;
  private JTextField _userDataPath;
  
  /**
   * Constructor.
   * @param parent .
   * @param appPathInstance .
   */
  public AppPathConfigurationPanelController(AreaController parent,
                                                     EnumApplicationPath appPathInstance)
  {
    super(parent);
    _appPathInstance=appPathInstance;
    switch (appPathInstance)
    {
      case APPLICATION_PATH_CONFIGURATION:
      default:
        _label="Companion";
        break;
      case LOTRO_APPLICATION_PATH_CONFIGURATION:
        _label="LOTRO";
        break;
    }
    setPanel(buildPanel());
  }

  private JPanel buildPanel()
  {
    JPanel panel=GuiFactory.buildPanel(new GridBagLayout());
    int y=0;
    GridBagConstraints c;
    if (_appPathInstance != EnumApplicationPath.APPLICATION_PATH_CONFIGURATION) {
      // App path
      c=new GridBagConstraints(0,y,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
      panel.add(GuiFactory.buildLabel("Application directory:"),c); // I18n
      _appPath=GuiFactory.buildTextField("");
      _appPath.setColumns(PATH_SIZE);
      c=new GridBagConstraints(1,y,1,1,1.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(5,5,5,5),0,0);
      panel.add(_appPath,c);
      JButton appPathChooseButton=GuiFactory.buildButton("Choose..."); // I18n
      appPathChooseButton.addActionListener(
          new ActionListener()
          {
            @Override
            public void actionPerformed(ActionEvent e)
            {
              doChooseAppPath();
            }
          }
      );
      c=new GridBagConstraints(2,y,1,1,0.0,0.0,GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
      panel.add(appPathChooseButton,c);
      ++y;
    }

    // User path
    c=new GridBagConstraints(0,y,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
    panel.add(GuiFactory.buildLabel("User directory:"),c); // I18n
    _userPath=GuiFactory.buildTextField("");
    _userPath.setColumns(PATH_SIZE);
    c=new GridBagConstraints(1,y,1,1,1.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(5,5,5,5),0,0);
    panel.add(_userPath,c);
    JButton userPathChooseButton=GuiFactory.buildButton("Choose..."); // I18n
    userPathChooseButton.addActionListener(
        new ActionListener()
        {
          @Override
          public void actionPerformed(ActionEvent e)
          {
            doChooseUserPath();
          }
        }
    );
    c=new GridBagConstraints(2,y,1,1,0.0,0.0,GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
    panel.add(userPathChooseButton,c);
    ++y;
    
    if (_appPathInstance == EnumApplicationPath.APPLICATION_PATH_CONFIGURATION) {
      // Data path
      c=new GridBagConstraints(0,y,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
      panel.add(GuiFactory.buildLabel("User data directory:"),c); // I18n
      _userDataPath=GuiFactory.buildTextField("");
      _userDataPath.setColumns(PATH_SIZE);
      c=new GridBagConstraints(1,y,1,1,1.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(5,5,5,5),0,0);
      panel.add(_userDataPath,c);
      JButton userDataPathChooseButton=GuiFactory.buildButton("Choose..."); // I18n
      userDataPathChooseButton.addActionListener(
          new ActionListener()
          {
            @Override
            public void actionPerformed(ActionEvent e)
            {
              doChooseUserDataPath();
            }
          }
      );
      c=new GridBagConstraints(2,y,1,1,0.0,0.0,GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
      panel.add(userDataPathChooseButton,c);
    }

    panel.setBorder(GuiFactory.buildTitledBorder(String.format("%s Client", _label))); // I18n
    return panel;
  }

  /**
   * Set the configuration to show.
   * @param appPathConfig Configuration to show.
   */
  public void setConfig(ApplicationPathConfiguration appPathConfig)
  {
    if (_appPathInstance != EnumApplicationPath.APPLICATION_PATH_CONFIGURATION) {
      // App path
      _appPath.setText(appPathConfig.getPath().toString());
    }
    // User path
    _userPath.setText(appPathConfig.getUserPath().toString());
    
    if (_appPathInstance == EnumApplicationPath.APPLICATION_PATH_CONFIGURATION) {
      // User data path
      _userDataPath.setText(
          ((CompanionApplicationPathConfiguration)appPathConfig).getDataConfiguration().getRootPath().toString()
      );
    }
  }

  /**
   * Save the configuration to the given storage.
   * @param appPathConfig to use.
   * @param modificationStatus {@code ModificationStatus}
   */
  public void saveTo(ApplicationPathConfiguration appPathConfig,
                     ModificationStatus modificationStatus)
  {
    if (_appPathInstance != EnumApplicationPath.APPLICATION_PATH_CONFIGURATION) {
      // App path
      appPathConfig.setPath(Paths.get(_appPath.getText()));
    }
    // User path
    appPathConfig.setUserPath(Paths.get(_userPath.getText()));
    
    if (_appPathInstance == EnumApplicationPath.APPLICATION_PATH_CONFIGURATION) {
      // User data path
      ((CompanionApplicationPathConfiguration)appPathConfig).getDataConfiguration().setRootPath(new File(_userDataPath.getText()));
    }
  }

  private void doChooseAppPath()
  {
    FileChooserController ctrl=new FileChooserController(
        "ApplicationConfiguration",
        String.format("Choose %s path...", _label) // I18n
    );
    ctrl.getChooser().setCurrentDirectory(new File(_userPath.getText()));
    File datDir=ctrl.chooseDirectory(getWindowController().getWindow(), "OK"); // I18n
    if (datDir!=null)
    {
      _userPath.setText(datDir.getAbsolutePath());
    }
  }

  private void doChooseUserPath()
  {
    FileChooserController ctrl=new FileChooserController(
        "ApplicationConfiguration",
        String.format("Choose %s user path...", _label) // I18n
    );
    ctrl.getChooser().setCurrentDirectory(new File(_userPath.getText()));
    File datDir=ctrl.chooseDirectory(getWindowController().getWindow(), "OK"); // I18n
    if (datDir!=null)
    {
      _userPath.setText(datDir.getAbsolutePath());
    }
  }
  
  private void doChooseUserDataPath()
  {
    FileChooserController ctrl=new FileChooserController(
        "ApplicationConfiguration",
        String.format("Choose %s user data path...", _label) // I18n
    );
    ctrl.getChooser().setCurrentDirectory(new File(_userPath.getText()));
    File datDir=ctrl.chooseDirectory(getWindowController().getWindow(), "OK"); // I18n
    if (datDir!=null)
    {
      _userDataPath.setText(datDir.getAbsolutePath());
    }
  }
  
  /**
   * @return application path.
   */
  public String getAppPath()
  {
    return _appPath.getText();
  }

  @Override
  public void dispose()
  {
    super.dispose();
    _userDataPath=null;
    _userPath=null;
    _appPath=null;
  }
}
