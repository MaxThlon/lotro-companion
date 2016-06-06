package delta.games.lotro.gui.log;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.log.CharacterLog;
import delta.games.lotro.character.log.CharacterLogItemsFilter;
import delta.games.lotro.character.log.CharacterLogsManager;
import delta.games.lotro.gui.utils.GuiFactory;
import delta.games.lotro.utils.gui.DefaultWindowController;

/**
 * Controller for a "character log" window.
 * @author DAM
 */
public class CharacterLogWindowController extends DefaultWindowController
{
  private CharacterLogFilterController _filterController;
  private CharacterLogPanelController _panelController;
  private CharacterLogTableController _tableController;
  private CharacterFile _toon;
  private CharacterLogItemsFilter _filter;

  /**
   * Constructor.
   * @param toon Managed toon.
   */
  public CharacterLogWindowController(CharacterFile toon)
  {
    _toon=toon;
    _filter=new CharacterLogItemsFilter();
  }

  private CharacterLog getLog()
  {
    CharacterLog log=_toon.getLastCharacterLog();
    return log;
  }

  /**
   * Get the window identifier for a given toon.
   * @param serverName Server name.
   * @param toonName Toon name.
   * @return A window identifier.
   */
  public static String getIdentifier(String serverName, String toonName)
  {
    String id="LOG#"+serverName+"#"+toonName;
    id=id.toUpperCase();
    return id;
  }

  @Override
  protected JFrame build()
  {
    JFrame frame=super.build();
    String name=_toon.getName();
    String title="Character log for: "+name;
    frame.setTitle(title);
    frame.pack();
    frame.setMinimumSize(new Dimension(400,300));
    return frame;
  }
  

  @Override
  public String getWindowIdentifier()
  {
    String serverName=_toon.getServerName();
    String toonName=_toon.getName();
    String id=getIdentifier(serverName,toonName);
    return id;
  }

  @Override
  protected JComponent buildContents()
  {
    CharacterLog log=getLog();
    JPanel logPanel=GuiFactory.buildPanel(new GridBagLayout());
    _tableController=new CharacterLogTableController(log,_filter);
    // Log frame
    _panelController=new CharacterLogPanelController(_tableController);
    JPanel tablePanel=_panelController.getPanel();
    // Filter
    _filterController=new CharacterLogFilterController(log,_filter,_panelController);
    JPanel filterPanel=_filterController.getPanel();
    TitledBorder filterBorder=GuiFactory.buildTitledBorder("Filter");
    filterPanel.setBorder(filterBorder);
    // Whole panel
    GridBagConstraints c=new GridBagConstraints(0,0,1,1,1,0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0);
    logPanel.add(filterPanel,c);
    c.gridy=1;c.weighty=1;c.fill=GridBagConstraints.BOTH;
    logPanel.add(tablePanel,c);
    return logPanel;
  }

  /**
   * Update contents.
   */
  public void update()
  {
    CharacterLogsManager logsManager=_toon.getLogsManager();
    CharacterLog log=logsManager.getLastLog();
    _filterController.setLog(log);
    _panelController.setLog(log);
  }

  /**
   * Release all managed resources.
   */
  @Override
  public void dispose()
  {
    super.dispose();
    if (_tableController!=null)
    {
      _tableController.dispose();
      _tableController=null;
    }
    // TODO dispose on panel and filter
    _toon=null;
  }
}
