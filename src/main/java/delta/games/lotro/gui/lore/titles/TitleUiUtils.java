package delta.games.lotro.gui.lore.titles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;

import delta.common.ui.swing.combobox.ComboBoxController;
import delta.common.ui.swing.labels.HyperLinkController;
import delta.common.ui.swing.labels.LocalHyperlinkAction;
import delta.common.ui.swing.navigator.NavigatorWindowController;
import delta.common.ui.swing.navigator.PageIdentifier;
import delta.common.ui.swing.windows.WindowController;
import delta.common.ui.swing.windows.WindowsManager;
import delta.games.lotro.gui.common.navigation.ReferenceConstants;
import delta.games.lotro.gui.common.rewards.RewardsUiUtils;
import delta.games.lotro.gui.navigation.NavigatorFactory;
import delta.games.lotro.lore.titles.TitleDescription;
import delta.games.lotro.lore.titles.TitlesManager;

/**
 * Utility methods for title-related UIs.
 * @author DAM
 */
public class TitleUiUtils
{
  /**
   * Build a combo-box controller to choose a title category.
   * @return A new combo-box controller.
   */
  public static ComboBoxController<String> buildCategoryCombo()
  {
    ComboBoxController<String> ctrl=new ComboBoxController<String>();
    ctrl.addEmptyItem("");
    List<String> categories=TitlesManager.getInstance().getCategories();
    for(String category : categories)
    {
      ctrl.addItem(category,category);
    }
    ctrl.selectItem(null);
    return ctrl;
  }

  /**
   * Build a title link controller.
   * @param parent Parent window.
   * @param title Title to use.
   * @param label Label to use.
   * @return a new controller.
   */
  public static HyperLinkController buildTitleLink(final WindowController parent, final TitleDescription title, JLabel label)
  {
    ActionListener al=new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        showTitleWindow(parent,title.getIdentifier());
      }
    };
    String text=(title!=null)?title.getName():"???";
    text=RewardsUiUtils.getDisplayedTitle(text);
    LocalHyperlinkAction action=new LocalHyperlinkAction(text,al);
    HyperLinkController controller=new HyperLinkController(action,label);
    return controller;
  }

  /**
   * Show a title display window.
   * @param parent Parent window.
   * @param titleID Title identifier.
   */
  public static void showTitleWindow(WindowController parent, int titleID)
  {
    NavigatorWindowController window=null;
    if (parent instanceof NavigatorWindowController)
    {
      window=(NavigatorWindowController)parent;
    }
    else
    {
      WindowsManager windowsMgr=parent.getWindowsManager();
      int id=windowsMgr.getAll().size();
      window=NavigatorFactory.buildNavigator(parent,id);
      windowsMgr.registerWindow(window);
    }
    PageIdentifier ref=ReferenceConstants.getTitleReference(titleID);
    window.navigateTo(ref);
    window.show(false);
  }
}

