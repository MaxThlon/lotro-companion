package delta.games.lotro.utils.plugin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import delta.common.framework.plugin.PluginManager;
import delta.common.ui.swing.area.AbstractAreaController;
import delta.common.ui.swing.area.AreaController;
import delta.common.ui.swing.tables.CellDataProvider;
import delta.common.ui.swing.tables.DefaultTableColumnController;
import delta.common.ui.swing.tables.GenericTableController;
import delta.common.ui.swing.tables.ListDataProvider;
import delta.common.ui.swing.tables.TableColumnsManager;
import delta.games.lotro.character.status.traitPoints.TraitPoint;
import delta.games.lotro.client.plugin.Plugin;
import delta.games.lotro.lore.quests.Achievable;

/**
 * @author MaxThlon
 */
public class PluginTableController extends AbstractAreaController
{
  // Manager
  PluginManager _pluginManager;
  // Data
  //private TypedProperties _prefs;
  List<Plugin> _plugins;
  
  // GUI
  private JTable _table;
  private GenericTableController<Plugin> _tableController;

  /**
   * Constructor
   * @param parent .
   * @param pluginManager . 
   */
  public PluginTableController(AreaController parent,
                               PluginManager pluginManager) {
    super(parent);
    _pluginManager=pluginManager;
    _plugins=_pluginManager.getPlugins();

    _tableController=buildTable();
  }
  
  private GenericTableController<Plugin> buildTable()
  {
    ListDataProvider<Plugin> provider=new ListDataProvider<Plugin>(_plugins);

    GenericTableController<Plugin> table=new GenericTableController<Plugin>(provider);
    
    List<String> columnsIds=getColumnIds();
    List<DefaultTableColumnController<Plugin,?>> columns=buildColumns(table, columnsIds);
    for(DefaultTableColumnController<Plugin,?> column : columns)
    {
      table.addColumnController(column);
    }
    
    TableColumnsManager<Plugin> columnsManager=table.getColumnsManager();
    columnsManager.setColumns(columnsIds);
    return table;
  }
  
  /**
   * Build the columns for a plugin table.
   * @param table .
   * @param columnsIds .
   * @return A list of columns for a plugin table.
   */
  public List<DefaultTableColumnController<Plugin,?>> buildColumns(GenericTableController<Plugin> table,
                                                                   List<String> columnsIds)
  {
    List<DefaultTableColumnController<Plugin,?>> ret=new ArrayList<DefaultTableColumnController<Plugin,?>>();

    CellDataProvider<Plugin,String> keyCell=new CellDataProvider<Plugin,String>()
    {
      @Override
      public String getData(Plugin plugin)
      {
        return plugin.getInformation()._name;
      }
    };
    DefaultTableColumnController<Plugin,String> keyColumn=
        new DefaultTableColumnController<Plugin,String>("name", "name",String.class,keyCell); // 18n
    keyColumn.setWidthSpecs(100,-1,100);
    ret.add(keyColumn);

    DefaultTableColumnController<Plugin,String> startLuaColumn=table.buildButtonColumn("startLua", "startLua", 100);
    startLuaColumn.setActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        Plugin plugin=(Plugin)e.getSource();
        _pluginManager.startLua();
      }
    });
    ret.add(startLuaColumn);
    
    DefaultTableColumnController<Plugin,String> executeColumn=table.buildButtonColumn("execute", "execute", 100);
    executeColumn.setActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        Plugin plugin=(Plugin)e.getSource();
        _pluginManager.execute(plugin);
      }
    });
    ret.add(executeColumn);
    
    //ret.add(table.buildButtonColumn("translate", "translate", 100));

    return ret;
  }
  
  private List<String> getColumnIds()
  {
    List<String> columnIds=new ArrayList<String>();
    columnIds.add("name");
    columnIds.add("startLua");
    columnIds.add("execute");
    columnIds.add("translate");
    return columnIds;
  }
  
  /**
   * Get the managed table controller.
   * @return the managed table controller.
   */
  public GenericTableController<Plugin> getTableController()
  {
    return _tableController;
  }
  
  /**
   * Get the managed table.
   * @return the managed table.
   */
  public JTable getTable()
  {
    if (_table==null)
    {
      _table=_tableController.getTable();
    }
    return _table;
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    super.dispose();
    // Preferences
    /*if (_prefs!=null)
    {
      List<String> columnIds=_tableController.getColumnsManager().getSelectedColumnsIds();
      _prefs.setStringList(ItemChooser.COLUMNS_PROPERTY,columnIds);
      _prefs=null;
    }*/
    // GUI
    _table=null;
    if (_tableController!=null)
    {
      _tableController.dispose();
      _tableController=null;
    }
    // Data
    _plugins=null;
    // Manager
    _pluginManager=null;
  }
}
