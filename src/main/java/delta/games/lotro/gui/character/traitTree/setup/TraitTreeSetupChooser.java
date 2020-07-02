package delta.games.lotro.gui.character.traitTree.setup;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JDialog;

import delta.common.ui.swing.tables.GenericTableController;
import delta.common.ui.swing.windows.WindowController;
import delta.common.utils.misc.TypedProperties;
import delta.games.lotro.character.classes.traitTree.setup.TraitTreeSetup;
import delta.games.lotro.character.classes.traitTree.setup.TraitTreeSetupsManager;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.gui.character.traitTree.setup.table.TraitTreeSetupTableBuilder;
import delta.games.lotro.utils.gui.chooser.ObjectChoiceWindowController;

/**
 * Utility method to choose a trait tree setup.
 * @author DAM
 */
public class TraitTreeSetupChooser
{
  /**
   * Preference file for the columns of the trait tree setup chooser.
   */
  public static final String TRAIT_TREE_SETUP_CHOOSER_PROPERTIES_ID="TraitTreeSetupChooserColumn";
  /**
   * Preference file for the trait tree setup chooser.
   */
  public static final String TRAIT_TREE_SETUP_CHOOSER="TraitTreeSetupChooser";
  /**
   * Name of the property for column IDs.
   */
  public static final String COLUMNS_PROPERTY="columns";

  /**
   * Choose an essence.
   * @param parent Parent controller.
   * @param characterClass Character class to use.
   * @return A trait tree setup or <code>null</code>.
   */
  public static TraitTreeSetup chooseTraitTreeSetup(WindowController parent, CharacterClass characterClass)
  {
    TraitTreeSetupsManager setupsMgr=TraitTreeSetupsManager.getInstance();
    List<TraitTreeSetup> setups=setupsMgr.getSetups(characterClass);
    TypedProperties prefs=null;
    if (parent!=null)
    {
      prefs=parent.getUserProperties(TRAIT_TREE_SETUP_CHOOSER);
    }
    ObjectChoiceWindowController<TraitTreeSetup> chooser=buildChooser(parent,prefs,setups);
    TraitTreeSetup ret=chooser.editModal();
    chooser.dispose();
    return ret;
  }

  /**
   * Build a trait tree setup chooser window.
   * @param parent Parent controller.
   * @param prefs Preferences for this window.
   * @param setups Item to use.
   * @return the newly built chooser.
   */
  public static ObjectChoiceWindowController<TraitTreeSetup> buildChooser(WindowController parent, TypedProperties prefs, List<TraitTreeSetup> setups)
  {
    // Table
    GenericTableController<TraitTreeSetup> table=TraitTreeSetupTableBuilder.buildTable(setups);

    // Build and configure chooser
    final ObjectChoiceWindowController<TraitTreeSetup> chooser=new ObjectChoiceWindowController<TraitTreeSetup>(parent,prefs,table);
    JDialog dialog=chooser.getDialog();
    // Title
    dialog.setTitle("Choose trait tree setup:");
    // Dimension
    dialog.setMinimumSize(new Dimension(600,300));
    dialog.setSize(600,300);
    return chooser;
  }
}
