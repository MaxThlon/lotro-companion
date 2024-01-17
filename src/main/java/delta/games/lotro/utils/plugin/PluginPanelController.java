package delta.games.lotro.utils.plugin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;

import delta.common.framework.plugin.PluginManager;
import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.panels.AbstractPanelController;
import delta.common.ui.swing.windows.WindowController;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.gui.character.chooser.CharacterSelectionStateListener;
import delta.games.lotro.gui.character.chooser.CharacterSelectionStructureChangeEvent;
import delta.games.lotro.gui.character.chooser.CharacterSelectionStructureListener;
import delta.games.lotro.gui.character.chooser.CharactersSelectionPanelController;

/**
 * Controller for a panel that displays plugins.
 * @author MaxThlon
 */
public class PluginPanelController extends AbstractPanelController
                                   implements CharacterSelectionStateListener,
                                              CharacterSelectionStructureListener
{
  // Manager
  private PluginManager _pluginManager;
  // Controllers
  private CharactersSelectionPanelController _toonSelectionController;
  private PluginTableController _pluginTableController;
  private LotroLuaEnvTreeController _lotroLuaEnvTreeController;
  // Data
  private List<CharacterFile> _toons;
  // UI

  /**
   * Constructor.
   * @param parent .
   */
  public PluginPanelController(WindowController parent)
  {
    super(parent);
    _pluginManager=PluginManager.getInstance();
    _toons=new ArrayList<CharacterFile>();

    JPanel panel=build(parent);
    setPanel(panel);
  }

  private JPanel build(WindowController parent)
  {
    JPanel pluginPanel=buildPluginPanel();

    return pluginPanel;
  }

  private JPanel buildPluginPanel()
  {
    JPanel panel = GuiFactory.buildPanel(null);
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    // Plugin panel
    JPanel pluginPanel = GuiFactory.buildPanel(new BorderLayout());
    pluginPanel.setBorder(GuiFactory.buildTitledBorder("Plugins"));

    // Toons selection
    _toonSelectionController=new CharactersSelectionPanelController((WindowController)getParentController(), _toons);
    _toonSelectionController.getStateListenersManager().addListener(this);
    _toonSelectionController.getStructureListenersManager().addListener(this);
    JPanel toonsControlPanel=_toonSelectionController.getPanel();
    pluginPanel.add(toonsControlPanel, BorderLayout.PAGE_START);

    // Plugin list
    _pluginTableController=new PluginTableController(this, _pluginManager, _toons);
    JTable table=_pluginTableController.getTable();
    pluginPanel.add(GuiFactory.buildScrollPane(table));
    panel.add(pluginPanel);

    // PluginManager panel
    JPanel pluginManagerPanel = GuiFactory.buildPanel(new BorderLayout());
    pluginManagerPanel.setBorder(GuiFactory.buildTitledBorder("Environment"));

    JButton refreshEnvButton = GuiFactory.buildButton("refresh");
    pluginManagerPanel.add(refreshEnvButton, BorderLayout.PAGE_START);

    // PluginManager tree
    _lotroLuaEnvTreeController = new LotroLuaEnvTreeController(this, _pluginManager);
    JTree tree = _lotroLuaEnvTreeController.getTree();
    pluginManagerPanel.add(GuiFactory.buildScrollPane(tree));

    refreshEnvButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        _lotroLuaEnvTreeController.refresh();
      }
    });
    
    panel.add(pluginManagerPanel);

    JPanel mainPanel = GuiFactory.buildPanel(new BorderLayout());
    mainPanel.add(GuiFactory.buildScrollPane(panel));
    return mainPanel;
  }

  @Override
  public void selectionStructureChanged(CharacterSelectionStructureChangeEvent event)
  {
    for(CharacterFile addedToon : event.getAddedToons())
    {
      _toons.add(addedToon);
    }
    for(CharacterFile removedToon : event.getRemovedToons())
    {
      _toons.remove(removedToon);
    }
  }

  @Override
  public void selectionStateChanged(String toonId, boolean selected)
  {
    /*if (_chartController!=null)
    {
      _chartController.setVisible(toonId,selected);
    }*/
  }

  /**
   * Set translations to display.
   */
  public void updatePlugins()
  {
  }

  /**
   * Update values.
   */
  public void update()
  {
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Data
    _toons=null;
    // Controllers
    _pluginTableController=null;
    _toonSelectionController=null;
    // Manager
    _pluginManager=null;
    // Inherited
    super.dispose();
  }
}
