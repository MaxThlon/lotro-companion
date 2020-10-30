package delta.games.lotro.gui.maps.resources;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.windows.WindowController;
import delta.games.lotro.gui.utils.ItemIconController;
import delta.games.lotro.lore.items.Item;

/**
 * Controller for a panel to display items in a row.
 * @author DAM
 */
public class ItemsController
{
  private WindowController _parent;
  private List<ItemIconController> _icons;
  private JPanel _panel;

  /**
   * Constructor.
   * @param parent Parent window controller.
   */
  public ItemsController(WindowController parent)
  {
    _parent=parent;
    _icons=new ArrayList<ItemIconController>();
    _panel=GuiFactory.buildPanel(new FlowLayout());
  }

  /**
   * Get the managed panel.
   * @return the managed panel.
   */
  public JPanel getPanel()
  {
    return _panel;
  }

  /**
   * Set the items to display.
   * @param items Items to display.
   */
  public void setItems(List<Item> items)
  {
    disposeItems();
    for(Item item : items)
    {
      ItemIconController icon=new ItemIconController(_parent);
      icon.setItem(item,1);
      _icons.add(icon);
      _panel.add(icon.getIcon());
    }
    _panel.revalidate();
    _panel.repaint();
  }

  private void disposeItems()
  {
    _panel.removeAll();
    for(ItemIconController icon : _icons)
    {
      icon.dispose();
    }
    _icons.clear();
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    if (_icons!=null)
    {
      disposeItems();
      _icons=null;
    }
    _parent=null;
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
  }
}
