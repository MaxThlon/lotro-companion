package delta.games.lotro.utils.cfg;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.filechooser.FileSystemView;

import delta.common.utils.application.config.path.ApplicationPathConfiguration;
import delta.common.utils.misc.Preferences;
import delta.common.utils.misc.TypedProperties;
import delta.games.lotro.config.UserConfig;
import delta.games.lotro.dat.data.DatConfiguration;

/**
 * @author MaxThlon
 */
public class LotroApplicationPathConfiguration implements ApplicationPathConfiguration
{
  // DAT
  private static final String DAT_CONFIGURATION="DatConfiguration";
  private static final String CLIENT_PATH="ClientPath";
  
  // User path
  private static final String LOTRO_PATH_CONFIGURATION="LotroPathConfiguration";
  private static final String USER_PATH="UserPath";
  
  private static final Path DEFAULT_USER_PATH=Paths.get(
      FileSystemView.getFileSystemView().getDefaultDirectory().getPath(),
      "The Lord of the Rings Online"
  );
  
  private DatConfiguration _datConfiguration;
  private Path _userPath;

  /**
   * Constructor.
   */
  public LotroApplicationPathConfiguration() {
    super();
    _datConfiguration=new DatConfiguration();
    _userPath=DEFAULT_USER_PATH;
  }
  
  /**
   * Get the DatConfiguration.
   * @return a DatConfiguration.
   */
  public DatConfiguration getDatConfiguration()
  {
    return _datConfiguration;
  }

  @Override
  public Path getPath()
  {
    return _datConfiguration.getRootPath().toPath();
  }
  
  @Override
  public void setPath(Path path)
  {
    _datConfiguration.setRootPath(path.toFile());
  }
  
  @Override
  public Path getUserPath()
  {
    return _userPath;
  }

  @Override
  public void setUserPath(Path userPath)
  {
    _userPath=userPath;
  }

  /**
   * Initialize from preferences.
   * @param preferences Preferences to use.
   */
  public void fromPreferences(Preferences preferences)
  {
    TypedProperties props=preferences.getPreferences(DAT_CONFIGURATION);
    String path=props.getStringProperty(CLIENT_PATH, getPath().toString());
    setPath(Paths.get(path));

    props=preferences.getPreferences(LOTRO_PATH_CONFIGURATION);
    String userPath=props.getStringProperty(USER_PATH, getUserPath().toString());
    setUserPath(Paths.get(userPath));
  }
  
  /**
   * Save configuration.
   * @param userCfg User configuration.
   */
  public void save(UserConfig userCfg)
  {
    String clientPath=_datConfiguration.getRootPath().getAbsolutePath();
    userCfg.setStringValue(DAT_CONFIGURATION,CLIENT_PATH,clientPath);

    userCfg.setStringValue(LOTRO_PATH_CONFIGURATION,USER_PATH,getUserPath().toString());
  }
}
