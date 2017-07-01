package delta.games.lotro.gui.stats.levelling;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import delta.common.ui.swing.windows.DefaultWindowController;
import delta.games.lotro.Config;
import delta.games.lotro.Preferences;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;
import delta.games.lotro.stats.level.MultipleToonsLevellingStats;
import delta.games.lotro.utils.TypedProperties;

/**
 * Controller for a "character level" window.
 * @author DAM
 */
public class CharacterLevelWindowController extends DefaultWindowController
{
  private static final String LEVELLING_PREFERENCES_NAME="levelling";
  private static final String TOON_NAME_PREFERENCE="levelling.registered.toon";

  private CharacterLevelPanelController _levellingPanelController;
  private MultipleToonsLevellingStats _stats;

  /**
   * Constructor.
   */
  public CharacterLevelWindowController()
  {
    _stats=new MultipleToonsLevellingStats();
    Preferences preferences=Config.getInstance().getPreferences();
    TypedProperties props=preferences.getPreferences(LEVELLING_PREFERENCES_NAME);
    List<String> toonIds=props.getStringList(TOON_NAME_PREFERENCE);
    CharactersManager manager=CharactersManager.getInstance();
    if ((toonIds!=null) && (toonIds.size()>0))
    {
      for(String toonID : toonIds)
      {
        CharacterFile toon=manager.getToonById(toonID);
        if (toon!=null)
        {
          _stats.addToon(toon);
        }
      }
    }
    _levellingPanelController=new CharacterLevelPanelController(this,_stats);
  }

  @Override
  protected JComponent buildContents()
  {
    JPanel panel=_levellingPanelController.getPanel();
    return panel;
  }

  /**
   * Get the window identifier for this window.
   * @return A window identifier.
   */
  public static String getIdentifier()
  {
    return "LEVELLING";
  }

  @Override
  protected JFrame build()
  {
    JFrame frame=super.build();
    // Title
    String title="Characters levelling";
    frame.setTitle(title);
    frame.pack();
    frame.setMinimumSize(new Dimension(500,380));
    frame.setSize(new Dimension(700,500));
    return frame;
  }

  @Override
  public String getWindowIdentifier()
  {
    return getIdentifier();
  }

  /**
   * Release all managed resources.
   */
  @Override
  public void dispose()
  {
    super.dispose();
    List<CharacterFile> toons=_stats.getToonsList();
    Preferences preferences=Config.getInstance().getPreferences();
    TypedProperties props=preferences.getPreferences(LEVELLING_PREFERENCES_NAME);
    List<String> toonIds=new ArrayList<String>();
    for(CharacterFile toon : toons)
    {
      toonIds.add(toon.getIdentifier());
    }
    props.setStringList(TOON_NAME_PREFERENCE,toonIds);
    _stats=null;
    if (_levellingPanelController!=null)
    {
      _levellingPanelController.dispose();
      _levellingPanelController=null;
    }
  }
}
