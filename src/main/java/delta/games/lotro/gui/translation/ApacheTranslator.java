package delta.games.lotro.gui.translation;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import delta.common.utils.i18n.Translator;
import delta.games.lotro.gui.utils.l10n.Labels;

/**
 * @author MaxThlon
 */
public class ApacheTranslator extends Translator implements TranslatorEx
{
  

  private PropertiesConfiguration _propertiesConfiguration;
  
  /**
   * @param baseName of the <tt>ResourceBundle</tt> to use.
   * @param parent translator.
   * @param locale Locale.
   */
  public ApacheTranslator(String baseName, Translator parent, Locale locale)
  {
    super(baseName,parent, locale);
    _propertiesConfiguration = new PropertiesConfiguration();
    
    try
    {
      _propertiesConfiguration.getLayout().load(
          _propertiesConfiguration,
          new InputStreamReader(getApplicationLocaleUrl(_locale).openStream())
      );
    }
    catch (ConfigurationException|IOException e)
    {
      LOGGER.error(e);
      
    }
  }

  /**
   * Get the application locale url.
   * @param locale 
   * @return a locale file url.
   */
  private URL getApplicationLocaleUrl(Locale locale) {
    ResourceBundle.Control control=ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_PROPERTIES);

    Locale usedLocale;
    if (locale.getLanguage() == "en") {
      usedLocale=Locale.ROOT;
    } else {
      usedLocale=locale;
    }

    return Labels.class.getClassLoader().getResource(
        control.toResourceName(
            control.toBundleName(Labels.class.getName(), usedLocale),
            "properties"
        )
    );
  }
  
  @Override
  public String getComment(String key) {
    return _propertiesConfiguration.getLayout().getComment(key);
  }
  
  @Override
  public String unsafeTranslate(String key) {
    return _propertiesConfiguration.getString(key);
  }
  
  /**
   * @param key
   * @param value
   */
  public void setTranslation(String key, String value) {
    _propertiesConfiguration.setProperty(key, value);
  }
  
  @Override
  public Set<String> getKeys() {
    return _propertiesConfiguration.getLayout().getKeys();
  }
}
