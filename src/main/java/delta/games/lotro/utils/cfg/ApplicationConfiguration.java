package delta.games.lotro.utils.cfg;

import delta.common.framework.plugin.PluginConfiguration;
import delta.common.framework.plugin.PluginConfigurationHolder;
import delta.common.utils.ListenersManager;
import delta.common.utils.application.config.main.MainApplicationConfiguration;
import delta.common.utils.application.config.path.ApplicationPathConfiguration;
import delta.common.utils.l10n.L10nConfiguration;
import delta.common.utils.l10n.dates.DateFormatID;
import delta.common.utils.l10n.numbers.NumberFormatID;
import delta.games.lotro.config.DataConfiguration;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.config.UserConfig;
import delta.games.lotro.config.labels.LabelsConfiguration;
import delta.games.lotro.config.path.CompanionApplicationPathConfiguration;
import delta.games.lotro.config.path.EnumApplicationPath;
import delta.games.lotro.dat.data.DatConfiguration;
import delta.games.lotro.gui.translation.TranslationConfiguration;
import delta.games.lotro.utils.gui.LotroGuiPatternConfiguration;
import delta.games.lotro.utils.plugin.LotroPluginConfiguration;

/**
 * Configuration of the LotroCompanion application.
 * @author DAM
 */
public class ApplicationConfiguration extends MainApplicationConfiguration implements PluginConfigurationHolder
{
  private static class ApplicationConfigurationHolder {
    private static final ApplicationConfiguration APPLICATION_CONFIGURATION = new ApplicationConfiguration();
  }
  
  // L10n
  private static final String L10N_CONFIGURATION="Localization";
  private static final String DATE_FORMAT="DateFormat";
  private static final String DATETIME_FORMAT="DateTimeFormat";
  private static final String NUMBER_FORMAT="NumberFormat";

  private CompanionApplicationPathConfiguration _appPathConfiguration;
  private LotroApplicationPathConfiguration _lotroAppPathConfiguration;
  private L10nConfiguration _l10nConfiguration;
  private LabelsConfiguration _labelsConfiguration;
  private LotroGuiPatternConfiguration _guiPatternConfiguration;
  private LotroPluginConfiguration _lotroPluginConfiguration;
  private TranslationConfiguration _translationConfiguration;
  private ListenersManager<ConfigurationListener> _listeners;

  /**
   * Get the application configuration.
   * @return the application configuration.
   */
  public static final ApplicationConfiguration getInstance()
  {
    return ApplicationConfigurationHolder.APPLICATION_CONFIGURATION;
  }

  /**
   * Constructor.
   */
  private ApplicationConfiguration()
  {
    super();
    initConfiguration();
    _listeners=new ListenersManager<ConfigurationListener>();
  }

  /**
   * Get the application path configuration.
   * @return the application path configuration.
   */
  public ApplicationPathConfiguration getAppPathConfiguration()
  {
    return _appPathConfiguration;
  }
  
  /**
   * Get the lotro path configuration.
   * @return the lotro path configuration.
   */
  public LotroApplicationPathConfiguration getLotroPathConfiguration()
  {
    return _lotroAppPathConfiguration;
  }
  
  /**
   * Get the path configuration by UserPathInstance.
   * @param appPathInstance .
   * @return the path configuration.
   */
  public ApplicationPathConfiguration getPathConfigurationMap(EnumApplicationPath appPathInstance)
  {
    switch (appPathInstance)
    {
      case APPLICATION_PATH_CONFIGURATION:
      default:
        return getAppPathConfiguration();
      case LOTRO_APPLICATION_PATH_CONFIGURATION:
        return getLotroPathConfiguration();
    }
  }

  /**
   * Get the DAT configuration. 
   * @return the DAT configuration.
   */
  public DatConfiguration getDatConfiguration()
  {
    return _lotroAppPathConfiguration.getDatConfiguration();
  }

  /**
   * Get the localization configuration. 
   * @return the localization configuration.
   */
  public L10nConfiguration getL10nConfiguration()
  {
    return _l10nConfiguration;
  }

  /**
   * Get the data configuration. 
   * @return the data configuration.
   */
  public DataConfiguration getDataConfiguration()
  {
    return _appPathConfiguration.getDataConfiguration();
  }

  /**
   * Get the labels configuration.
   * @return the labels configuration.
   */
  public LabelsConfiguration getLabelsConfiguration()
  {
    return _labelsConfiguration;
  }

  /**
   * Get the gui pattern configuration.
   * @return the gui pattern configuration.
   */
  public LotroGuiPatternConfiguration getGuiPatternConfiguration()
  {
    return _guiPatternConfiguration;
  }
  
  /**
   * Get the plugin configuration.
   * @return the lotro plugin configuration.
   */
  public PluginConfiguration getPluginConfiguration()
  {
    return _lotroPluginConfiguration;
  }
  
  /**
   * Get the translation configuration.
   * @return the translation configuration.
   */
  public TranslationConfiguration getTranslationConfiguration()
  {
    return _translationConfiguration;
  }
  
  /**
   * Get the configuration listeners.
   * @return the configuration listeners.
   */
  public ListenersManager<ConfigurationListener> getListeners()
  {
    return _listeners;
  }

  private void initConfiguration()
  {
    UserConfig config=UserConfig.getInstance();
    // Application path configuration
    _appPathConfiguration=LotroCoreConfig.getInstance().getAppPathConfiguration();
    // Lotro path configuration
    _lotroAppPathConfiguration=new LotroApplicationPathConfiguration();
    _lotroAppPathConfiguration.fromPreferences(LotroCoreConfig.getInstance().getPreferences());
    // Localization
    _l10nConfiguration=new L10nConfiguration();
    String dateFormat=config.getStringValue(L10N_CONFIGURATION,DATE_FORMAT,DateFormatID.AUTO);
    _l10nConfiguration.setDateFormatID(dateFormat);
    String dateTimeFormat=config.getStringValue(L10N_CONFIGURATION,DATETIME_FORMAT,DateFormatID.AUTO);
    _l10nConfiguration.setDateTimeFormatID(dateTimeFormat);
    String integerFormat=config.getStringValue(L10N_CONFIGURATION,NUMBER_FORMAT,NumberFormatID.AUTO);
    _l10nConfiguration.setNumberFormatID(integerFormat);
    // Labels
    _labelsConfiguration=LotroCoreConfig.getInstance().getLabelsConfiguration();
    // Gui patterns
    _guiPatternConfiguration=new LotroGuiPatternConfiguration(this);
    _guiPatternConfiguration.fromPreferences(LotroCoreConfig.getInstance().getPreferences());
    // plugins
    _lotroPluginConfiguration=new LotroPluginConfiguration(this);
    _lotroPluginConfiguration.fromPreferences(LotroCoreConfig.getInstance().getPreferences());
    // Translation
    _translationConfiguration=new TranslationConfiguration();
    // Save...
    saveConfiguration();
  }

  /**
   * Save configuration.
   */
  public void saveConfiguration()
  {
    UserConfig userCfg=UserConfig.getInstance();
    // Application path configuration
    _appPathConfiguration.save(userCfg);
    // Lotro path configuration
    _lotroAppPathConfiguration.save(userCfg);

    // Default formats
    String dateFormat=_l10nConfiguration.getDateFormatID();
    userCfg.setStringValue(L10N_CONFIGURATION,DATE_FORMAT,dateFormat);
    String dateTimeFormat=_l10nConfiguration.getDateTimeFormatID();
    userCfg.setStringValue(L10N_CONFIGURATION,DATETIME_FORMAT,dateTimeFormat);
    String numberFormat=_l10nConfiguration.getNumberFormatID();
    userCfg.setStringValue(L10N_CONFIGURATION,NUMBER_FORMAT,numberFormat);
    // Labels
    _labelsConfiguration.save(userCfg);
    // Gui patterns
    _guiPatternConfiguration.save(LotroCoreConfig.getInstance().getPreferences());
    // Plugins
    _lotroPluginConfiguration.save(LotroCoreConfig.getInstance().getPreferences());

    // Save configuration
    UserConfig.getInstance().save();
  }

  /**
   * Called when the configuration has been updated.
   * @param modificationStatus {@code ModificationStatus}
   */
  public void configurationUpdated(ModificationStatus modificationStatus)
  {
    for(ConfigurationListener listener : _listeners)
    {
      listener.configurationUpdated(this, modificationStatus);
    }
  }
}
