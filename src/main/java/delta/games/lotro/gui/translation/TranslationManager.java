package delta.games.lotro.gui.translation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;

import delta.common.utils.i18n.MultilocalesTranslator;
import delta.games.lotro.config.labels.AvailableLabelsDefinition;
import delta.games.lotro.config.labels.DefinitionOfAvailableLabels;
import delta.games.lotro.gui.utils.l10n.Labels;

/**
 * Manager for translations.
 * @author MaxThlon
 */
public class TranslationManager
{
  private static final Logger LOGGER=Logger.getLogger(TranslationManager.class);

  private static TranslationManager _instance=null;
  //private TranslationConfiguration _translationConfiguration;
  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static TranslationManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new TranslationManager();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  private TranslationManager()
  {
    //ApplicationConfiguration applicationCfg=ApplicationConfiguration.getInstance();
    //_translationConfiguration=applicationCfg.getTranslationConfiguration();
  }

  /**
   * Get application locales.
   * @return a locale list.
   */
  public List<Locale> getApplicationLocales()
  {
    DefinitionOfAvailableLabels _availableCfgs=new DefinitionOfAvailableLabels();
    AvailableLabelsDefinition _availableLabelsDefinition=_availableCfgs.getAppLabels();

    return _availableLabelsDefinition.getEntries().stream().map((entry) ->
      Locale.forLanguageTag(entry.getKey())
    ).collect(Collectors.toList());
  }
  
  /**
   * @return ApacheMultilocalesTranslator.
   */
  public ApacheMultilocalesTranslator getApacheMultilocalesTranslator() {
    return new ApacheMultilocalesTranslator(
        Labels.class.getName(),
        getApplicationLocales()
    );
  }

  /**
   * @return ApacheMultilocalesTranslator.
   */
  public MultilocalesTranslator getPluginMultilocalesTranslator() {
    return null;
  }

  /**
   * compute diff.
   * @param input1 
   * @param input2 
   * @return git diff OutputStream.
   */
  public OutputStream computeDiff(final byte[] input1, final byte[] input2)
  {
    OutputStream out = new ByteArrayOutputStream();
    
    RawText rt1 = new RawText(input1);
    RawText rt2 = new RawText(input2);
    EditList diffList = new EditList();
    diffList.addAll(new HistogramDiff().diff(RawTextComparator.DEFAULT, rt1, rt2));
    try
    {
      new DiffFormatter(out).format(diffList, rt1, rt2);
    }
    catch (IOException e)
    {
      LOGGER.error(e);
    }

    return out;
  }
}
