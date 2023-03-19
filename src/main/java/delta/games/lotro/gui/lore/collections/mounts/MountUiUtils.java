package delta.games.lotro.gui.lore.collections.mounts;

import java.util.List;

import delta.common.ui.swing.combobox.ComboBoxController;
import delta.games.lotro.common.enums.SkillCharacteristicSubCategory;
import delta.games.lotro.lore.collections.mounts.MountsManager;

/**
 * Utility methods for mount-related UIs.
 * @author DAM
 */
public class MountUiUtils
{
  /**
   * Build a combo-box controller to choose a mount category.
   * @return A new combo-box controller.
   */
  public static ComboBoxController<SkillCharacteristicSubCategory> buildCategoryCombo()
  {
    ComboBoxController<SkillCharacteristicSubCategory> ctrl=new ComboBoxController<SkillCharacteristicSubCategory>();
    ctrl.addEmptyItem("");
    List<SkillCharacteristicSubCategory> categories=MountsManager.getInstance().getCategories();
    for(SkillCharacteristicSubCategory category : categories)
    {
      ctrl.addItem(category,category.getLabel());
    }
    ctrl.selectItem(null);
    return ctrl;
  }
}
