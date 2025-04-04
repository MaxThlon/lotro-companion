package delta.games.lotro.gui.lore.crafting.recipes.explorer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.tables.TableColumnsChooserController;
import delta.common.ui.swing.windows.WindowController;
import delta.games.lotro.gui.lore.crafting.recipes.RecipesTableController;
import delta.games.lotro.gui.lore.items.FilterUpdateListener;
import delta.games.lotro.gui.utils.l10n.Labels;
import delta.games.lotro.lore.crafting.recipes.Recipe;

/**
 * Controller the recipe explorer panel.
 * @author DAM
 */
public class RecipeExplorerPanelController implements FilterUpdateListener
{
  // Data
  private RecipesTableController _tableController;
  // GUI
  private JPanel _panel;
  private JLabel _statsLabel;
  // Controllers
  private WindowController _parent;

  /**
   * Constructor.
   * @param parent Parent window.
   * @param tableController Associated table controller.
   */
  public RecipeExplorerPanelController(WindowController parent, RecipesTableController tableController)
  {
    _parent=parent;
    _tableController=tableController;
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
    TitledBorder itemsFrameBorder=GuiFactory.buildTitledBorder("Items"); // 18n
    panel.setBorder(itemsFrameBorder);

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
        TableColumnsChooserController<Recipe> chooser=new TableColumnsChooserController<Recipe>(_parent,_tableController.getTableController());
        chooser.editModal();
      }
    };
    choose.addActionListener(al);
    statsPanel.add(choose);
    panel.add(statsPanel,BorderLayout.NORTH);
    return panel;
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
    String label=Labels.getCountLabel("Recipe(s)",nbFiltered,nbItems); // I18n
    _statsLabel.setText(label);
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Data
    _tableController=null;
    // GUI
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
    _statsLabel=null;
    // Controllers
    _parent=null;
  }
}
