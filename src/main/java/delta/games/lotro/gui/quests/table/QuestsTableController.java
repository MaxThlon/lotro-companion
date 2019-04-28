package delta.games.lotro.gui.quests.table;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import delta.common.ui.swing.tables.CellDataProvider;
import delta.common.ui.swing.tables.DefaultTableColumnController;
import delta.common.ui.swing.tables.GenericTableController;
import delta.common.ui.swing.tables.ListDataProvider;
import delta.common.ui.swing.tables.ProxiedTableColumnController;
import delta.common.ui.swing.tables.TableColumnController;
import delta.common.ui.swing.tables.TableColumnsManager;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.misc.TypedProperties;
import delta.games.lotro.common.ChallengeLevel;
import delta.games.lotro.common.ChallengeLevelComparator;
import delta.games.lotro.common.Repeatability;
import delta.games.lotro.common.Size;
import delta.games.lotro.common.requirements.UsageRequirement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.gui.common.requirements.table.RequirementsColumnsBuilder;
import delta.games.lotro.gui.common.rewards.table.RewardsColumnsBuilder;
import delta.games.lotro.gui.items.chooser.ItemChooser;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestDescription.FACTION;
import delta.games.lotro.lore.quests.QuestsManager;

/**
 * Controller for a table that shows quests.
 * @author DAM
 */
public class QuestsTableController
{
  // Data
  private TypedProperties _prefs;
  private List<QuestDescription> _quests;
  // GUI
  private JTable _table;
  private GenericTableController<QuestDescription> _tableController;

  /**
   * Constructor.
   * @param prefs Preferences.
   * @param filter Managed filter.
   */
  public QuestsTableController(TypedProperties prefs, Filter<QuestDescription> filter)
  {
    _prefs=prefs;
    _quests=new ArrayList<QuestDescription>();
    init();
    _tableController=buildTable();
    _tableController.setFilter(filter);
  }

  private GenericTableController<QuestDescription> buildTable()
  {
    ListDataProvider<QuestDescription> provider=new ListDataProvider<QuestDescription>(_quests);
    GenericTableController<QuestDescription> table=new GenericTableController<QuestDescription>(provider);
    List<TableColumnController<QuestDescription,?>> columns=buildColumns();
    for(TableColumnController<QuestDescription,?> column : columns)
    {
      table.addColumnController(column);
    }

    TableColumnsManager<QuestDescription> columnsManager=table.getColumnsManager();
    List<String> columnsIds=getColumnIds();
    columnsManager.setColumns(columnsIds);
    return table;
  }

  /**
   * Build the columns for a quests table.
   * @return A list of columns for a quests table.
   */
  public static List<TableColumnController<QuestDescription,?>> buildColumns()
  {
    List<TableColumnController<QuestDescription,?>> ret=new ArrayList<TableColumnController<QuestDescription,?>>();
    // Identifier column
    {
      CellDataProvider<QuestDescription,Integer> idCell=new CellDataProvider<QuestDescription,Integer>()
      {
        @Override
        public Integer getData(QuestDescription quest)
        {
          return Integer.valueOf(quest.getIdentifier());
        }
      };
      DefaultTableColumnController<QuestDescription,Integer> idColumn=new DefaultTableColumnController<QuestDescription,Integer>(QuestColumnIds.ID.name(),"ID",Integer.class,idCell);
      idColumn.setWidthSpecs(100,100,100);
      ret.add(idColumn);
    }
    // Name column
    {
      CellDataProvider<QuestDescription,String> nameCell=new CellDataProvider<QuestDescription,String>()
      {
        @Override
        public String getData(QuestDescription quest)
        {
          return quest.getName();
        }
      };
      DefaultTableColumnController<QuestDescription,String> nameColumn=new DefaultTableColumnController<QuestDescription,String>(QuestColumnIds.NAME.name(),"Name",String.class,nameCell);
      nameColumn.setWidthSpecs(100,300,200);
      ret.add(nameColumn);
    }
    // Category column
    {
      CellDataProvider<QuestDescription,String> categoryCell=new CellDataProvider<QuestDescription,String>()
      {
        @Override
        public String getData(QuestDescription quest)
        {
          return quest.getCategory();
        }
      };
      DefaultTableColumnController<QuestDescription,String> categoryColumn=new DefaultTableColumnController<QuestDescription,String>(QuestColumnIds.CATEGORY.name(),"Category",String.class,categoryCell);
      categoryColumn.setWidthSpecs(80,350,80);
      ret.add(categoryColumn);
    }
    // Quest arc column
    {
      CellDataProvider<QuestDescription,String> questArcCell=new CellDataProvider<QuestDescription,String>()
      {
        @Override
        public String getData(QuestDescription quest)
        {
          return quest.getQuestArc();
        }
      };
      DefaultTableColumnController<QuestDescription,String> questArcColumn=new DefaultTableColumnController<QuestDescription,String>(QuestColumnIds.QUEST_ARC.name(),"Quest arc",String.class,questArcCell);
      questArcColumn.setWidthSpecs(80,350,80);
      ret.add(questArcColumn);
    }
    // Challenge level column
    {
      CellDataProvider<QuestDescription,ChallengeLevel> levelCell=new CellDataProvider<QuestDescription,ChallengeLevel>()
      {
        @Override
        public ChallengeLevel getData(QuestDescription quest)
        {
          return quest.getChallengeLevel();
        }
      };
      DefaultTableColumnController<QuestDescription,ChallengeLevel> levelColumn=new DefaultTableColumnController<QuestDescription,ChallengeLevel>(QuestColumnIds.LEVEL.name(),"Level",ChallengeLevel.class,levelCell);
      levelColumn.setWidthSpecs(100,100,100);
      levelColumn.setComparator(new ChallengeLevelComparator());
      ret.add(levelColumn);
    }
    // Recommended size column
    {
      CellDataProvider<QuestDescription,Size> sizeCell=new CellDataProvider<QuestDescription,Size>()
      {
        @Override
        public Size getData(QuestDescription quest)
        {
          return quest.getSize();
        }
      };
      DefaultTableColumnController<QuestDescription,Size> sizeColumn=new DefaultTableColumnController<QuestDescription,Size>(QuestColumnIds.SIZE.name(),"Size",Size.class,sizeCell);
      sizeColumn.setWidthSpecs(100,100,100);
      ret.add(sizeColumn);
    }
    // Faction column
    {
      CellDataProvider<QuestDescription,FACTION> factionCell=new CellDataProvider<QuestDescription,FACTION>()
      {
        @Override
        public FACTION getData(QuestDescription quest)
        {
          return quest.getFaction();
        }
      };
      DefaultTableColumnController<QuestDescription,FACTION> factionColumn=new DefaultTableColumnController<QuestDescription,FACTION>(QuestColumnIds.FACTION.name(),"Faction",FACTION.class,factionCell);
      factionColumn.setWidthSpecs(100,100,100);
      ret.add(factionColumn);
    }
    // Repeatable column
    {
      CellDataProvider<QuestDescription,Repeatability> repeatableCell=new CellDataProvider<QuestDescription,Repeatability>()
      {
        @Override
        public Repeatability getData(QuestDescription quest)
        {
          return quest.getRepeatability();
        }
      };
      DefaultTableColumnController<QuestDescription,Repeatability> repeatableColumn=new DefaultTableColumnController<QuestDescription,Repeatability>(QuestColumnIds.REPEATABLE.name(),"Repeatability",Repeatability.class,repeatableCell);
      repeatableColumn.setWidthSpecs(100,100,100);
      ret.add(repeatableColumn);
    }
    // Instanced column
    {
      CellDataProvider<QuestDescription,Boolean> instancedCell=new CellDataProvider<QuestDescription,Boolean>()
      {
        @Override
        public Boolean getData(QuestDescription quest)
        {
          return Boolean.valueOf(quest.isInstanced());
        }
      };
      DefaultTableColumnController<QuestDescription,Boolean> instancedColumn=new DefaultTableColumnController<QuestDescription,Boolean>(QuestColumnIds.INSTANCED.name(),"Instanced",Boolean.class,instancedCell);
      instancedColumn.setWidthSpecs(100,100,100);
      ret.add(instancedColumn);
    }
    // Shareable column
    {
      CellDataProvider<QuestDescription,Boolean> shareableCell=new CellDataProvider<QuestDescription,Boolean>()
      {
        @Override
        public Boolean getData(QuestDescription quest)
        {
          return Boolean.valueOf(quest.isShareable());
        }
      };
      DefaultTableColumnController<QuestDescription,Boolean> shareableColumn=new DefaultTableColumnController<QuestDescription,Boolean>(QuestColumnIds.SHAREABLE.name(),"Shareable",Boolean.class,shareableCell);
      shareableColumn.setWidthSpecs(100,100,100);
      ret.add(shareableColumn);
    }
    // Session play column
    {
      CellDataProvider<QuestDescription,Boolean> sessionPlayCell=new CellDataProvider<QuestDescription,Boolean>()
      {
        @Override
        public Boolean getData(QuestDescription quest)
        {
          return Boolean.valueOf(quest.isSessionPlay());
        }
      };
      DefaultTableColumnController<QuestDescription,Boolean> sessionPlayColumn=new DefaultTableColumnController<QuestDescription,Boolean>(QuestColumnIds.SESSION_PLAY.name(),"Session Play",Boolean.class,sessionPlayCell);
      sessionPlayColumn.setWidthSpecs(100,100,100);
      ret.add(sessionPlayColumn);
    }
    // Auto-bestowed column
    {
      CellDataProvider<QuestDescription,Boolean> autoBestowedCell=new CellDataProvider<QuestDescription,Boolean>()
      {
        @Override
        public Boolean getData(QuestDescription quest)
        {
          return Boolean.valueOf(quest.isAutoBestowed());
        }
      };
      DefaultTableColumnController<QuestDescription,Boolean> autoBestowedColumn=new DefaultTableColumnController<QuestDescription,Boolean>(QuestColumnIds.AUTO_BESTOWED.name(),"Auto-bestowed",Boolean.class,autoBestowedCell);
      autoBestowedColumn.setWidthSpecs(100,100,100);
      ret.add(autoBestowedColumn);
    }
    // Obsolete column
    {
      CellDataProvider<QuestDescription,Boolean> obsoleteCell=new CellDataProvider<QuestDescription,Boolean>()
      {
        @Override
        public Boolean getData(QuestDescription quest)
        {
          return Boolean.valueOf(quest.isObsolete());
        }
      };
      DefaultTableColumnController<QuestDescription,Boolean> obsoleteColumn=new DefaultTableColumnController<QuestDescription,Boolean>(QuestColumnIds.OBSOLETE.name(),"Obsolete",Boolean.class,obsoleteCell);
      obsoleteColumn.setWidthSpecs(100,100,100);
      ret.add(obsoleteColumn);
    }
    // Description column
    {
      CellDataProvider<QuestDescription,String> descriptionCell=new CellDataProvider<QuestDescription,String>()
      {
        @Override
        public String getData(QuestDescription quest)
        {
          return quest.getDescription();
        }
      };
      DefaultTableColumnController<QuestDescription,String> descriptionColumn=new DefaultTableColumnController<QuestDescription,String>(QuestColumnIds.DESCRIPTION.name(),"Description",String.class,descriptionCell);
      descriptionColumn.setWidthSpecs(100,-1,100);
      ret.add(descriptionColumn);
    }
    // Requirements
    {
      List<DefaultTableColumnController<UsageRequirement,?>> requirementColumns=RequirementsColumnsBuilder.buildRequirementsColumns();
      CellDataProvider<QuestDescription,UsageRequirement> dataProvider=new CellDataProvider<QuestDescription,UsageRequirement>()
      {
        @Override
        public UsageRequirement getData(QuestDescription quest)
        {
          return quest.getUsageRequirement();
        }
      };
      for(DefaultTableColumnController<UsageRequirement,?> requirementColumn : requirementColumns)
      {
        @SuppressWarnings("unchecked")
        TableColumnController<UsageRequirement,Object> c=(TableColumnController<UsageRequirement,Object>)requirementColumn;
        TableColumnController<QuestDescription,Object> proxiedColumn=new ProxiedTableColumnController<QuestDescription,UsageRequirement,Object>(c,dataProvider);
        ret.add(proxiedColumn);
      }
    }
    // Rewards
    {
      List<DefaultTableColumnController<Rewards,?>> rewardColumns=RewardsColumnsBuilder.buildRewardColumns();
      CellDataProvider<QuestDescription,Rewards> dataProvider=new CellDataProvider<QuestDescription,Rewards>()
      {
        @Override
        public Rewards getData(QuestDescription quest)
        {
          return quest.getQuestRewards();
        }
      };
      for(DefaultTableColumnController<Rewards,?> rewardColumn : rewardColumns)
      {
        @SuppressWarnings("unchecked")
        TableColumnController<Rewards,Object> c=(TableColumnController<Rewards,Object>)rewardColumn;
        TableColumnController<QuestDescription,Object> proxiedColumn=new ProxiedTableColumnController<QuestDescription,Rewards,Object>(c,dataProvider);
        ret.add(proxiedColumn);
      }
    }
    return ret;
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
      columnIds.add(QuestColumnIds.NAME.name());
      columnIds.add(QuestColumnIds.CATEGORY.name());
      columnIds.add(QuestColumnIds.LEVEL.name());
    }
    return columnIds;
  }

  /**
   * Get the managed table controller.
   * @return the managed table controller.
   */
  public GenericTableController<QuestDescription> getTableController()
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
    return _quests.size();
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

  private void reset()
  {
    _quests.clear();
  }

  private void init()
  {
    reset();
    QuestsManager manager=QuestsManager.getInstance();
    List<QuestDescription> quests=manager.getAll();
    for(QuestDescription quest : quests)
    {
      _quests.add(quest);
    }
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
   * Add an action listener.
   * @param al Action listener to add.
   */
  public void addActionListener(ActionListener al)
  {
    _tableController.addActionListener(al);
  }

  /**
   * Remove an action listener.
   * @param al Action listener to remove.
   */
  public void removeActionListener(ActionListener al)
  {
    _tableController.removeActionListener(al);
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
    _quests=null;
  }
}
