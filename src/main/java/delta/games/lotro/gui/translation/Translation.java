package delta.games.lotro.gui.translation;

import java.util.Locale;

import delta.common.utils.i18n.MultilocalesTranslator;
import delta.common.utils.i18n.Translator;

/**
 * @author MaxThlon
 */
public class Translation
{
  private MultilocalesTranslator _multilocalesTranslator;
  public String _key;
  
  
  /**
   * @param multilocalesTranslator .
   * @param key .
   */
  public Translation(MultilocalesTranslator multilocalesTranslator, String key) {
    _multilocalesTranslator=multilocalesTranslator;
    _key=key;
  }
  
  /**
   * @param locale
   * @return a translated string.
   */
  public String getTranslation(Locale locale) {
    return _multilocalesTranslator.getTranslator(locale).unsafeTranslate(_key);
  }

  /**
   * @param locale .
   * @param value .
   */
  public void setTranslation(Locale locale, String value) {
    Translator translator=_multilocalesTranslator.getTranslator(locale);
    
    if (translator instanceof TranslatorEx) {
      ((TranslatorEx)translator).setTranslation(_key, value);
    }
  }
}
