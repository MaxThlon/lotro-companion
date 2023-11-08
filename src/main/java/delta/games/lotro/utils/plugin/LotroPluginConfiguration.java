package delta.games.lotro.utils.plugin;

import java.nio.file.Path;
import java.nio.file.Paths;

import delta.common.framework.plugin.PluginConfiguration;
import delta.common.utils.application.config.path.ApplicationPathConfiguration;
import delta.common.utils.misc.TypedProperties;
import delta.games.lotro.config.path.EnumApplicationPath;
import delta.games.lotro.utils.cfg.ApplicationConfiguration;

/**
 * Configuration for user paths.
 * @author MaxThlon
 */
public class LotroPluginConfiguration extends PluginConfiguration
{
  private static final String LOTRO_PLUGIN_USER_PATH_INSTANCE="userPathInstance";
  private static final Path LOTRO_PLUGINS_PATH=Paths.get("Plugins");

  private ApplicationConfiguration _applicationCfg;
  private EnumApplicationPath _pluginAppPath;

  /**
   * Constructor.
   * @param applicationCfg .
   */
  public LotroPluginConfiguration(ApplicationConfiguration applicationCfg) {
    super(applicationCfg.getAppPathConfiguration());
    _applicationCfg=applicationCfg;
    _pluginAppPath=EnumApplicationPath.APPLICATION_PATH_CONFIGURATION;
  }
  
  /**
   * Get the PluginAppPath.
   * @return a PluginAppPath.
   */
  public EnumApplicationPath getPluginAppPath()
  {
    return _pluginAppPath;
  }
  
  /**
   * Set the PluginAppPath.
   * @param pluginAppPath .
   */
  public void setPluginAppPath(EnumApplicationPath pluginAppPath)
  {
    _pluginAppPath=pluginAppPath;
  }

  /**
   * Get the user data path for plugins.
   * @return a directory path.
   */
  @Override
  public Path getPluginsPath()
  {
    Path pluginsPath;
    switch (_pluginAppPath)
    {
      case APPLICATION_PATH_CONFIGURATION:
      default:
        pluginsPath=super.getPluginsPath();
        break;

      case LOTRO_APPLICATION_PATH_CONFIGURATION: {
        ApplicationPathConfiguration appPathConfig=_applicationCfg.getLotroPathConfiguration();
        
        pluginsPath=appPathConfig.getUserPath().resolve(LOTRO_PLUGINS_PATH);
        break;
      }
    }
    return pluginsPath;
  }
  
  @Override
  public Path getPluginDataPath()
  {
    Path pluginDataPath;
    switch (_pluginAppPath)
    {
      case APPLICATION_PATH_CONFIGURATION:
      default:
        pluginDataPath=super.getPluginDataPath();
        break;

      case LOTRO_APPLICATION_PATH_CONFIGURATION: {
        ApplicationPathConfiguration appPathConfig=_applicationCfg.getLotroPathConfiguration();
        pluginDataPath=appPathConfig.getUserPath().resolve(PLUGIN_DATA_PATH);
        break;
      }
    }
    return pluginDataPath;
  }

  @Override
  protected void FromProperties(TypedProperties props) {
    super.FromProperties(props);

    String userPathInstanceString=props.getStringProperty(LOTRO_PLUGIN_USER_PATH_INSTANCE, _pluginAppPath.name());
    setPluginAppPath(EnumApplicationPath.valueOf(userPathInstanceString));
  }
  
  @Override
  protected void save(TypedProperties props)
  {
    super.save(props);
    props.setStringProperty(LOTRO_PLUGIN_USER_PATH_INSTANCE, _pluginAppPath.name());
  }
}
