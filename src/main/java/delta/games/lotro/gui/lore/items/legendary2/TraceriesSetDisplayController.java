package delta.games.lotro.gui.lore.items.legendary2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.labels.MultilineLabel2;
import delta.common.ui.swing.windows.WindowController;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.legendary2.LegendaryInstance2;
import delta.games.lotro.lore.items.legendary2.SocketEntryInstance;
import delta.games.lotro.lore.items.legendary2.SocketsSetup;
import delta.games.lotro.lore.items.legendary2.SocketsSetupInstance;

/**
 * Controller for a panel to display a traceries set.
 * @author DAM
 */
public class TraceriesSetDisplayController
{
  // Data
  private ItemInstance<? extends Item> _itemInstance;
  private SocketsSetupInstance _traceries;
  // Controllers
  private List<SingleTraceryDisplayController> _controllers;
  // UI
  private JPanel _panel;

  /**
   * Constructor.
   * @param parent Parent window.
   * @param itemInstance Item to display.
   */
  public TraceriesSetDisplayController(WindowController parent, ItemInstance<? extends Item> itemInstance)
  {
    _itemInstance=itemInstance;
    LegendaryInstance2 leg2=(LegendaryInstance2)itemInstance;
    _traceries=leg2.getLegendaryAttributes().getSocketsSetup();
    _controllers=new ArrayList<SingleTraceryDisplayController>();
    SocketsSetup socketsSetup=_traceries.getSetupTemplate();
    int nbSlots=socketsSetup.getSocketsCount();
    for(int i=0;i<nbSlots;i++)
    {
      SingleTraceryDisplayController controller=new SingleTraceryDisplayController(parent);
      _controllers.add(controller);
    }
    _panel=GuiFactory.buildPanel(new GridBagLayout());
    fillPanel();
  }

  /**
   * Get the managed panel.
   * @return the managed panel.
   */
  public JPanel getPanel()
  {
    return _panel;
  }

  private void fillPanel()
  {
    _panel.removeAll();
    int baseLine=0;
    int index=0;
    for(SingleTraceryDisplayController controller : _controllers)
    {
      SocketEntryInstance socketInstance=_traceries.getEntry(index);
      index++;
      if (!isEnabled(socketInstance))
      {
        continue;
      }
      // Icon
      JButton icon=controller.getIcon();
      GridBagConstraints c=new GridBagConstraints(0,baseLine,1,2,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
      _panel.add(icon,c);
      // Label
      MultilineLabel2 label=controller.getNameGadget();
      c=new GridBagConstraints(1,baseLine,1,1,1.0,0.0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,5),0,0);
      _panel.add(label,c);
      // Stats
      MultilineLabel2 stats=controller.getStatsGadget();
      c=new GridBagConstraints(1,baseLine+1,1,1,1.0,0.0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,5),0,0);
      _panel.add(stats,c);
      baseLine+=2;
    }
  }

  /**
   * Indicates if the given socket is enabled or not.
   * @param socketInstance Socket to use.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isEnabled(SocketEntryInstance socketInstance)
  {
    Integer itemLevel=_itemInstance.getEffectiveItemLevel();
    int itemLevelInt=(itemLevel!=null)?itemLevel.intValue():1;
    int unlockItemLevel=socketInstance.getTemplate().getUnlockItemLevel();
    return itemLevelInt>=unlockItemLevel;
  }

  /**
   * Update gadgets to reflect the current state of the associated sockets setup.
   */
  public void update()
  {
    int size=_controllers.size();
    for(int i=0;i<size;i++)
    {
      SocketEntryInstance entry=_traceries.getEntry(i);
      _controllers.get(i).setTracery(entry);
    }
    fillPanel();
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Data
    _itemInstance=null;
    _traceries=null;
    // Controllers
    if (_controllers!=null)
    {
      for(SingleTraceryDisplayController controller : _controllers)
      {
        controller.dispose();
      }
      _controllers=null;
    }
    // UI
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
  }
}
