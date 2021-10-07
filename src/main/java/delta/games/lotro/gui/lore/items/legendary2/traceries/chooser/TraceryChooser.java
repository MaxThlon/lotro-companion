package delta.games.lotro.gui.lore.items.legendary2.traceries.chooser;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JDialog;

import delta.common.ui.swing.tables.GenericTableController;
import delta.common.ui.swing.windows.WindowController;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.misc.TypedProperties;
import delta.games.lotro.common.enums.SocketType;
import delta.games.lotro.gui.lore.items.legendary2.traceries.TraceriesFilterController;
import delta.games.lotro.gui.lore.items.legendary2.traceries.table.TraceriesTableBuilder;
import delta.games.lotro.lore.items.legendary2.TraceriesManager;
import delta.games.lotro.lore.items.legendary2.Tracery;
import delta.games.lotro.lore.items.legendary2.filters.TraceryFilter;
import delta.games.lotro.utils.gui.chooser.ObjectChoiceWindowController;

/**
 * Controller for an item choice window.
 * @author DAM
 */
public class TraceryChooser
{
  /**
   * Preference file for the columns of the tracery chooser.
   */
  public static final String TRACERY_CHOOSER_PROPERTIES_ID="TraceryChooserColumn";
  /**
   * Name of the property for column IDs.
   */
  public static final String COLUMNS_PROPERTY="columns";

  /**
   * Build a tracery chooser window.
   * @param parent Parent controller.
   * @param prefs Preferences for this window.
   * @param traceries Traceries to use.
   * @param filter Filter to use.
   * @param filterController Filter UI to use.
   * @return the newly built chooser.
   */
  public static ObjectChoiceWindowController<Tracery> buildChooser(WindowController parent, TypedProperties prefs, List<Tracery> traceries, Filter<Tracery> filter, TraceriesFilterController filterController)
  {
    // Table
    GenericTableController<Tracery> traceriesTable=TraceriesTableBuilder.buildTable(traceries);

    // Build and configure chooser
    final ObjectChoiceWindowController<Tracery> chooser=new ObjectChoiceWindowController<Tracery>(parent,prefs,traceriesTable);
    // Filter
    chooser.setFilter(filter,filterController);
    JDialog dialog=chooser.getDialog();
    // Title
    dialog.setTitle("Choose tracery:");
    // Dimension
    dialog.setMinimumSize(new Dimension(400,300));
    dialog.setSize(1000,dialog.getHeight());
    return chooser;
  }

  /**
   * Show the tracery selection dialog.
   * @param parent Parent controller.
   * @param selectedTracery Selected tracery.
   * @param type Type.
   * @return The selected tracery or <code>null</code> if the window was closed or canceled.
   */
  public static Tracery selectTracery(WindowController parent, Tracery selectedTracery, SocketType type)
  {
    List<Tracery> traceries=TraceriesManager.getInstance().getTracery(type);
    TraceryFilter filter=new TraceryFilter();
    // TODO Use props
    TraceriesFilterController filterController=new TraceriesFilterController(filter,traceries,null);
    // Build chooser
    ObjectChoiceWindowController<Tracery> chooser=TraceryChooser.buildChooser(parent,null,traceries,filter,filterController);
    // Show modal
    Tracery ret=chooser.editModal();
    return ret;
  }
}
