package delta.games.lotro.utils.plugin;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import delta.common.framework.plugin.PluginManager;
import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.panels.AbstractPanelController;
import delta.common.ui.swing.windows.WindowController;

/**
 * Controller for a panel that displays plugins.
 * @author MaxThlon
 */
public class PluginPanelController extends AbstractPanelController
{
  // Manager
  private PluginManager _pluginManager;
  // Controllers
  private PluginTableController _pluginTableController;
  // Data
  // UI

  /**
   * Constructor.
   * @param parent .
   */
  public PluginPanelController(WindowController parent)
  {
    super(parent);
    _pluginManager=PluginManager.getInstance();
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
    JPanel panel = GuiFactory.buildPanel(new BorderLayout());
    // List
    _pluginTableController=new PluginTableController(this, _pluginManager);
    JTable table=_pluginTableController.getTable();
    // Whole panel
    TitledBorder border=GuiFactory.buildTitledBorder("Main");
    panel.setBorder(border);
    JScrollPane jScrollPane = GuiFactory.buildScrollPane(table);
    panel.add(jScrollPane);
    return panel;
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
    // Controllers
    _pluginTableController=null;
    // Manager
    _pluginManager=null;
    // Inherited
    super.dispose();
  }
}
