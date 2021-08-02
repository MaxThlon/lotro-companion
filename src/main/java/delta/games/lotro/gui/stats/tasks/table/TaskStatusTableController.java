package delta.games.lotro.gui.stats.tasks.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import delta.common.ui.swing.tables.GenericTableController;
import delta.common.ui.swing.tables.ListDataProvider;
import delta.common.ui.swing.tables.TableColumnController;
import delta.common.ui.swing.tables.TableColumnsManager;
import delta.common.utils.misc.TypedProperties;
import delta.games.lotro.character.achievables.AchievableStatus;
import delta.games.lotro.character.achievables.AchievablesStatusManager;
import delta.games.lotro.character.tasks.TaskStatus;
import delta.games.lotro.character.tasks.filter.TaskStatusFilter;
import delta.games.lotro.gui.common.rewards.table.RewardsColumnIds;
import delta.games.lotro.gui.items.chooser.ItemChooser;
import delta.games.lotro.gui.quests.table.QuestColumnIds;
import delta.games.lotro.gui.stats.achievables.table.AchievableStatusColumnIds;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.tasks.Task;

/**
 * Controller for a table that shows the status of all tasks for a single character.
 * @author DAM
 */
public class TaskStatusTableController
{
  // Data
  private TypedProperties _prefs;
  private List<TaskStatus> _statuses;
  // GUI
  private JTable _table;
  private GenericTableController<TaskStatus> _tableController;

  /**
   * Constructor.
   * @param questsStatus Status to show.
   * @param prefs Preferences.
   * @param filter Managed filter.
   * @param tasks Tasks to use.
   */
  public TaskStatusTableController(AchievablesStatusManager questsStatus, TypedProperties prefs, TaskStatusFilter filter, List<Task> tasks)
  {
    _prefs=prefs;
    _statuses=new ArrayList<TaskStatus>();
    for(Task task : tasks)
    {
     QuestDescription quest=task.getQuest();
      AchievableStatus status=questsStatus.get(quest,true);
      TaskStatus taskStatus=new TaskStatus(task,status);
      _statuses.add(taskStatus);
    }
    _tableController=buildTable();
    _tableController.setFilter(filter);
  }

  private GenericTableController<TaskStatus> buildTable()
  {
    ListDataProvider<TaskStatus> provider=new ListDataProvider<TaskStatus>(_statuses);
    GenericTableController<TaskStatus> table=new GenericTableController<TaskStatus>(provider);
    // Columns
    List<TableColumnController<TaskStatus,?>> columns=TaskStatusColumnsBuilder.buildTaskStatusColumns();
    for(TableColumnController<TaskStatus,?> column : columns)
    {
      table.addColumnController(column);
    }
    List<String> columnsIds=getColumnIds();
    TableColumnsManager<TaskStatus> columnsManager=table.getColumnsManager();
    columnsManager.setColumns(columnsIds);

    return table;
  }

  private List<String> getColumnIds()
  {
    List<String> columnIds=null;
    if (_prefs!=null)
    {
      columnIds=_prefs.getStringList(ItemChooser.COLUMNS_PROPERTY);
    }
    if (columnIds==null)
    {
      columnIds=new ArrayList<String>();
      columnIds.add(AchievableStatusColumnIds.COMPLETED.name());
      columnIds.add(QuestColumnIds.NAME.name());
      columnIds.add(AchievableStatusColumnIds.COMPLETION_COUNT.name());
      columnIds.add(RewardsColumnIds.XP.name());
      columnIds.add(RewardsColumnIds.ITEM_XP.name());
      columnIds.add(RewardsColumnIds.MOUNT_XP.name());
    }
    return columnIds;
  }

  /**
   * Get the managed table controller.
   * @return the managed table controller.
   */
  public GenericTableController<TaskStatus> getTableController()
  {
    return _tableController;
  }

  /**
   * Update managed filter.
   */
  public void updateFilter()
  {
    _tableController.filterUpdated();
  }

  /**
   * Get the total number of quests.
   * @return A number of quests.
   */
  public int getNbItems()
  {
    return _statuses.size();
  }

  /**
   * Get the number of filtered items in the managed table.
   * @return A number of items.
   */
  public int getNbFilteredItems()
  {
    int ret=_tableController.getNbFilteredItems();
    return ret;
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
    // Preferences
    if (_prefs!=null)
    {
      List<String> columnIds=_tableController.getColumnsManager().getSelectedColumnsIds();
      _prefs.setStringList(ItemChooser.COLUMNS_PROPERTY,columnIds);
      _prefs=null;
    }
    // GUI
    _table=null;
    if (_tableController!=null)
    {
      _tableController.dispose();
      _tableController=null;
    }
    // Data
    _statuses=null;
  }
}
