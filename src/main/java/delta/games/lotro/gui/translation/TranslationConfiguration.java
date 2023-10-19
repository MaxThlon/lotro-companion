package delta.games.lotro.gui.translation;

import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import delta.common.utils.misc.Preferences;
import delta.common.utils.misc.TypedProperties;
import delta.games.lotro.gui.utils.l10n.Labels;

/**
 * Configuration for the translations.
 * @author MaxThlon
 */
public class TranslationConfiguration
{
  protected static final Logger LOGGER=Logger.getLogger(TranslationConfiguration.class);
  private static final String TRANSLATION_CONFIGURATION="Translation";

  /**
   * register path configuration
   * @param _virtualLinkManager
   /
  public static void registerPathConfiguration(VirtualLinkManager _virtualLinkManager) {

    UriLink labelsUriLink=null;
    try
    {
      labelsUriLink=new  UriLink(null, new URI(Labels.class.getName()));
    }
    catch (URISyntaxException e)
    {
      LOGGER.error(e);
    }

    DefinitionOfAvailableLabels _availableCfgs=new DefinitionOfAvailableLabels();
    AvailableLabelsDefinition _availableLabelsDefinition=_availableCfgs.getAppLabels();
    
    
    for(LabelsEntry labelEntry:_availableLabelsDefinition.getEntries())
    {
      _virtualLinkManager.registerVirtualLink(
          new LocaleLink(UUID.randomUUID(), Locale.forLanguageTag(labelEntry.getKey())),
          "applicationLocales"
      );
    }

    ParentedLink applicationLabelPaths=new ParentedLink(
        UUID.randomUUID(),
        labelsUriLink,
        new BracketLink(null, "applicationLocales")
    );
    _virtualLinkManager.registerVirtualLink(
        applicationLabelPaths,
        "applicationLabelPaths"
    );

    /*
    String baseName=Labels.class.getName();
    Locale locale;
    
    
    
    
    Parameters params = new Parameters();
    
  }*/

  /**
   * Constructor.
   */
  public TranslationConfiguration() {
  }

  /**
   * Initialize from preferences.
   * @param preferences Preferences to use.
   */
  public void fromPreferences(Preferences preferences)
  {
    TypedProperties props=preferences.getPreferences(TRANSLATION_CONFIGURATION);

    /*String guiPatternFactoryName=props.getStringProperty(GUI_PATTERN_FACTORY_NAME,getGuiPatternFactoryName());
    setGuiPatternFactoryName(guiPatternFactoryName);
    
    String guiPatternName=props.getStringProperty(GUI_PATTERN_NAME, _theme.getGuiPatternName());
    String themeName=props.getStringProperty(GUI_PATTERN_THEME_NAME, _theme.getThemeName());
    setTheme(new Theme(guiPatternName, themeName));

    Path defaultSkinPath=getSkinPath();
    String skinPath=props.getStringProperty(GUI_PATTERN_SKIN_PATH,(defaultSkinPath!=null)?defaultSkinPath.toString():null);
    setSkinPath((skinPath!=null)?Paths.get(skinPath):null);*/
  }
  
  /**
   * Save configuration.
   * @param preferences Preferences to use.
   */
  public void save(Preferences preferences)
  {
    TypedProperties props=preferences.getPreferences(TRANSLATION_CONFIGURATION);
    /*props.setStringProperty(GUI_PATTERN_FACTORY_NAME,_guiPatternFactoryName);
    props.setStringProperty(GUI_PATTERN_NAME,_theme.getGuiPatternName());
    if (_theme.getThemeName()==null) {
      props.removeProperty(GUI_PATTERN_THEME_NAME);
    } else {
      props.setStringProperty(GUI_PATTERN_THEME_NAME,_theme.getThemeName());
    }

    if (_skinPath==null) {
      props.removeProperty(GUI_PATTERN_SKIN_PATH);
    } else {
      props.setStringProperty(GUI_PATTERN_SKIN_PATH,_skinPath.toString());
    }*/
  }
}
