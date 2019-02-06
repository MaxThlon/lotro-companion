package delta.games.lotro;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.common.utils.misc.Preferences;
import delta.common.utils.misc.TypedProperties;
import delta.common.utils.text.EncodingNames;
import delta.common.utils.text.TextUtils;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Configuration.
 * @author DAM
 */
public final class Config
{
  private static Config _instance=new Config();

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
    LotroCoreConfig coreConfig=LotroCoreConfig.getInstance();

    // Lore
    _mapsDir=coreConfig.getFile(DataFiles.MAPS);

    // Configuration
    File parametersFiles=coreConfig.getFile(DataFiles.PARAMETERS);
    _parameters=new TypedProperties();
    _parameters.loadFromFile(parametersFiles);

    // User data
    File userDir=coreConfig.getUserDataDir();
    // - preferences
    File preferencesDir=new File(userDir,"preferences");
    _preferences=new Preferences(preferencesDir);

    // Load servers
    loadServers();
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
   * Get the maximum character level.
   * @return the maximum character level.
   */
  public int getMaxCharacterLevel()
  {
    TypedProperties props=getParameters();
    int maxLevel=props.getIntProperty("max.character.level",115);
    return maxLevel;
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
    _servers=new ArrayList<String>();
    File serversFiles=LotroCoreConfig.getInstance().getFile(DataFiles.SERVERS);
    List<String> servers=TextUtils.readAsLines(serversFiles,EncodingNames.UTF_8);
    if (servers!=null)
    {
      Collections.sort(servers);
      _servers.addAll(servers);
    }
  }
}
