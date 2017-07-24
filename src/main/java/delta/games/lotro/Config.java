package delta.games.lotro;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.common.utils.misc.Preferences;
import delta.common.utils.misc.TypedProperties;
import delta.common.utils.text.EncodingNames;
import delta.common.utils.text.TextUtils;

/**
 * Configuration.
 * @author DAM
 */
public class Config
{
  private static Config _instance=new Config();

  private File _rootDataDir;
  private File _configDir;
  private File _mapsDir;
  private TypedProperties _parameters;
  private Preferences _preferences;
  private List<String> _servers;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static Config getInstance()
  {
    return _instance;
  }

  /**
   * Private constructor.
   */
  private Config()
  {
    _rootDataDir=new File("data");
    _configDir=new File(_rootDataDir,"config");
    File loreDir=new File(_rootDataDir,"lore");
    _mapsDir=new File(loreDir,"maps");
    _servers=new ArrayList<String>();
    File parametersFiles=new File(_configDir,"params.txt");
    _parameters=new TypedProperties();
    _parameters.loadFromFile(parametersFiles);
    File preferencesDir=new File(_rootDataDir,"preferences");
    _preferences=new Preferences(preferencesDir);
    loadServers();
  }

  /**
   * Get the root storage directory for configuration files.
   * @return a directory.
   */
  public File getConfigDir()
  {
    return _configDir;
  }

  /**
   * Get the root storage directory for map files.
   * @return a directory.
   */
  public File getMapsDir()
  {
    return _mapsDir;
  }

  /**
   * Get the configuration parameters.
   * @return the configuration parameters.
   */
  public TypedProperties getParameters()
  {
    return _parameters;
  }

  /**
   * Get the preferences manager.
   * @return the preferences manager.
   */
  public Preferences getPreferences()
  {
    return _preferences;
  }

  /**
   * Get a list of known server names.
   * @return a list of known server names.
   */
  public List<String> getServerNames()
  {
    return _servers;
  }

  private void loadServers()
  {
    File serversFiles=new File(_configDir,"servers.txt");
    List<String> servers=TextUtils.readAsLines(serversFiles,EncodingNames.UTF_8);
    if (servers!=null)
    {
      Collections.sort(servers);
      _servers.addAll(servers);
    }
  }
}
