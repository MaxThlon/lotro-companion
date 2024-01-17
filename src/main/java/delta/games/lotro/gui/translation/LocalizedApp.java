package delta.games.lotro.gui.translation;

import delta.common.framework.translation.LocalizedEntity;
import delta.common.utils.i18n.MultilocalesTranslator;

/**
 * @author MaxThlon
 */
public class LocalizedApp extends LocalizedEntity
{
  /**
   * @param name .
   */
  public LocalizedApp(String name)
  {
    super(name);
  }
  
  @Override
  public MultilocalesTranslator getMultilocalesTranslator() {
    return TranslationManager.getInstance().getApacheMultilocalesTranslator();
  }
}
