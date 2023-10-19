package delta.games.lotro.utils.cfg;

/**
 * Listener for configuration changes.
 * @author DAM
 */
public interface ConfigurationListener
{
  /**
   * Called when the application configuration was updated.
   * @param newConfiguration {@code ApplicationConfiguration} the new configuration.
   * @param modificationStatus {@code ModificationStatus}
   */
  void configurationUpdated(ApplicationConfiguration newConfiguration,
                            ModificationStatus modificationStatus);
}
