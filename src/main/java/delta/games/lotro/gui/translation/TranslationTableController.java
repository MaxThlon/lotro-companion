package delta.games.lotro.gui.translation;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.area.AbstractAreaController;
import delta.common.ui.swing.area.AreaController;
import delta.common.ui.swing.panels.PanelController;
import delta.common.ui.swing.tables.CellDataProvider;
import delta.common.ui.swing.tables.CellDataUpdater;
import delta.common.ui.swing.tables.DefaultTableColumnController;
import delta.common.ui.swing.tables.GenericTableController;
import delta.common.ui.swing.tables.ListDataProvider;
import delta.common.ui.swing.tables.TableColumnsManager;

/**
 * @author MaxThlon
 */
public class TranslationTableController extends AbstractAreaController implements PanelController
{
  //Data
  //private TypedProperties _prefs;
  Set<Locale> _locales;
  List<Translation> _translations;
  
  // GUI
  private JPanel _panel;
  private JTable _table;
  private GenericTableController<Translation> _tableController;

  /**
   * Constructor
   * @param parent .
   * @param locales .
   * @param translations 
   */
  public TranslationTableController(AreaController parent,
                                    Set<Locale> locales,
                                    List<Translation> translations) {
    super(parent);
    _locales=locales;
    _translations=translations;

    _tableController=buildTable();
  }
  
  private GenericTableController<Translation> buildTable()
  {
    ListDataProvider<Translation> provider=new ListDataProvider<Translation>(_translations);
    
        //Arrays.asList(null));//translator.getKeys().stream().collect(Collectors.toList()));
    GenericTableController<Translation> table=new GenericTableController<Translation>(provider);
    
    List<String> columnsIds=getColumnIds();
    List<DefaultTableColumnController<Translation,?>> columns=buildColumns(columnsIds);
    for(DefaultTableColumnController<Translation,?> column : columns)
    {
      table.addColumnController(column);
    }
    
    TableColumnsManager<Translation> columnsManager=table.getColumnsManager();
    columnsManager.setColumns(columnsIds);
    return table;
  }
  
  /**
   * Build the columns for a recipes table.
   * @param columnsIds 
   * @return A list of columns for a recipes table.
   */
  public List<DefaultTableColumnController<Translation,?>> buildColumns(List<String> columnsIds)
  {
    List<DefaultTableColumnController<Translation,?>> ret=new ArrayList<DefaultTableColumnController<Translation,?>>();

    CellDataProvider<Translation,String> keyCell=new CellDataProvider<Translation,String>()
    {
      @Override
      public String getData(Translation translation)
      {
        return translation._key;
      }
    };
    DefaultTableColumnController<Translation,String> keyColumn=
        new DefaultTableColumnController<Translation,String>("keys", "keys",String.class,keyCell); // 18n
    keyColumn.setWidthSpecs(100,-1,200);
    ret.add(keyColumn);

    int currentIndex=1;
    for(Locale locale:_locales) {
      CellDataProvider<Translation,String> translationCell=new CellDataProvider<Translation,String>()
      {
        @Override
        public String getData(Translation translation)
        {
          return translation.getTranslation(locale);
        }
      };
      
      String columnId=columnsIds.get(currentIndex);
      DefaultTableColumnController<Translation,String> translationColumn=
          new DefaultTableColumnController<Translation,String>(columnId, columnId,String.class,translationCell); // 18n
      translationColumn.setWidthSpecs(100,-1,200);
      
      // Editor
      translationColumn.setCellEditor(new DefaultCellEditor(new JTextField()));
      // Updater
      CellDataUpdater<Translation> updater=new CellDataUpdater<Translation>()
      {
        @Override
        public void setData(Translation translation, Object value)
        {
          if (value instanceof String) {
            translation.setTranslation(locale, (String)value);
          }
          /*if (listener!=null)
          {
            listener.filterUpdated();
          }*/
        }
      };
      translationColumn.setValueUpdater(updater);
      translationColumn.setEditable(true);

      ret.add(translationColumn);

      ++currentIndex;
    }

    return ret;
  }
  
  private List<String> getColumnIds()
  {
    List<String> columnIds=new ArrayList<String>();
    columnIds.add("keys");
    for(Locale locale:_locales) {
      columnIds.add(locale.getDisplayName());
    }
    return columnIds;
  }
  
  /**
   * Get the managed table controller.
   * @return the managed table controller.
   */
  public GenericTableController<Translation> getTableController()
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

  @Override
  public JPanel getPanel()
  {
    if (_panel==null)
    {
      _panel=GuiFactory.buildPanel(new BorderLayout());
      _table=_tableController.getTable();
      _panel.add(GuiFactory.buildScrollPane(_table), BorderLayout.CENTER);
    }
    return _panel;
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
    _translations=null;
    _locales=null;
  }
}
