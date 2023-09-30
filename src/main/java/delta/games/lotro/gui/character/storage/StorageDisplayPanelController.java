package delta.games.lotro.gui.character.storage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.tables.TableColumnsChooserController;
import delta.common.ui.swing.windows.WindowController;
import delta.common.utils.misc.TypedProperties;
import delta.games.lotro.character.storage.StoredItem;
import delta.games.lotro.gui.lore.items.FilterUpdateListener;
import delta.games.lotro.gui.main.GlobalPreferences;
import delta.games.lotro.gui.utils.l10n.Labels;

/**
 * Controller for the storage display panel.
 * @author DAM
 */
public class StorageDisplayPanelController implements FilterUpdateListener
{
  // Data
  private List<StoredItem> _items;
  private StorageFilter _filter;
  // GUI
  private JPanel _panel;
  private JLabel _statsLabel;
  // Controllers
  private StoredItemsTableController _tableController;
  private WindowController _parent;

  /**
   * Constructor.
   * @param parent Parent window.
   * @param filter FIlter of stored items.
   */
  public StorageDisplayPanelController(WindowController parent, StorageFilter filter)
  {
    _parent=parent;
    _filter=filter;
    _items=new ArrayList<StoredItem>();
    TypedProperties prefs=GlobalPreferences.getGlobalProperties("StorageDisplay");
    _tableController=new StoredItemsTableController(parent,prefs,_items,filter);
    getPanel();
  }

  /**
   * Get the managed panel.
   * @return the managed panel.
   */
  public JPanel getPanel()
  {
    if (_panel==null)
    {
      _panel=build();
    }
    return _panel;
  }

  private JPanel build()
  {
    JPanel panel=GuiFactory.buildPanel(new BorderLayout());
    TitledBorder border=GuiFactory.buildTitledBorder("Items"); // I18n
    panel.setBorder(border);

    // Table
    JTable table=_tableController.getTable();
    JScrollPane scroll=GuiFactory.buildScrollPane(table);
    panel.add(scroll,BorderLayout.CENTER);
    // Stats
    JPanel statsPanel=GuiFactory.buildPanel(new FlowLayout(FlowLayout.LEFT));
    _statsLabel=GuiFactory.buildLabel("-");
    statsPanel.add(_statsLabel);
    JButton choose=GuiFactory.buildButton(Labels.getLabel("shared.chooseColumns.button"));
    ActionListener al=new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        TableColumnsChooserController<StoredItem> chooser=new TableColumnsChooserController<StoredItem>(_parent,_tableController.getTableController());
        chooser.editModal();
      }
    };
    choose.addActionListener(al);
    statsPanel.add(choose);
    panel.add(statsPanel,BorderLayout.NORTH);
    return panel;
  }

  /**
   * Update display.
   * @param items Items to show.
   */
  public void update(List<StoredItem> items)
  {
    updateStorage(items);
    updateStatsLabel();
    _tableController.update();
  }

  /**
   * Update filter.
   */
  public void filterUpdated()
  {
    _tableController.updateFilter();
    updateStatsLabel();
  }

  private void updateStatsLabel()
  {
    int nbFiltered=_tableController.getNbFilteredItems();
    int nbItems=_tableController.getNbItems();
    String label=Labels.getCountLabel(nbFiltered,nbItems);
    _statsLabel.setText(label);
  }

  private void updateStorage(List<StoredItem> items)
  {
    _items.clear();
    _items.addAll(items);
    _filter.getConfiguration().setItems(_items);
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Data
    _items=null;
    _filter=null;
    // GUI
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
    _statsLabel=null;
    // Controllers
    _tableController=null;
    _parent=null;
  }
}
