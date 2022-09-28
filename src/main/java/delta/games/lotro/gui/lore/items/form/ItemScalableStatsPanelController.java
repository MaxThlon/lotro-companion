package delta.games.lotro.gui.lore.items.form;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.tables.GenericTableController;
import delta.common.ui.swing.tables.export.DataExport;
import delta.common.ui.swing.tables.export.StringExportDataOutput;
import delta.common.ui.utils.clipboard.ClipboardIO;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.scaling.ItemScaling;
import delta.games.lotro.lore.items.scaling.ItemScalingBuilder;
import delta.games.lotro.lore.items.scaling.ItemScalingEntry;

/**
 * Controller for a panel to display a table of the scalable stats of an item.
 * @author DAM
 */
public class ItemScalableStatsPanelController
{
  // Data
  private Item _item;
  // GUI
  private JPanel _panel;
  // Controllers
  private GenericTableController<ItemScalingEntry> _table;

  /**
   * Constructor.
   * @param item Item to show.
   */
  public ItemScalableStatsPanelController(Item item)
  {
    _item=item;
    _panel=build();
  }

  /**
   * Get the managed panel.
   * @return the managed panel or <code>null</code> if no scaling.
   */
  public JPanel getPanel()
  {
    return _panel;
  }

  private JPanel build()
  {
    ItemScaling scaling=ItemScalingBuilder.build(_item);
    if (scaling.getEntries().size()==0)
    {
      return null;
    }
    // Table
    _table=ItemScalingTableBuilder.buildTable(scaling);
    JTable table=_table.getTable();
    JScrollPane scroll=GuiFactory.buildScrollPane(table);
    JPanel panel=GuiFactory.buildPanel(new GridBagLayout());
    GridBagConstraints c=new GridBagConstraints(0,1,1,1,1.0,1.0,GridBagConstraints.NORTHWEST,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
    panel.add(scroll,c);
    // Export
    JButton exportButton=buildExportButton();
    c=new GridBagConstraints(0,0,1,1,0.0,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.NONE,new Insets(2,5,2,5),0,0);
    panel.add(exportButton,c);
    return panel;
  }

  private JButton buildExportButton()
  {
    JButton ret=GuiFactory.buildButton("Export to clipboard");
    ActionListener al=new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        exportToClipboardAsCSV();
      }
    };
    ret.addActionListener(al);
    return ret;
  }

  private void exportToClipboardAsCSV()
  {
    StringExportDataOutput output=new StringExportDataOutput();
    DataExport<ItemScalingEntry> exporter=new DataExport<ItemScalingEntry>(output);
    exporter.export(_table);
    String result=output.getResult();
    ClipboardIO.writeText(result);
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Data
    _item=null;
    // UI
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
    // Controllers
    if (_table!=null)
    {
      _table.dispose();
      _table=null;
    }
  }
}
