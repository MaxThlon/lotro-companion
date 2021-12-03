package delta.games.lotro.gui.character.storage.own;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;

import delta.common.ui.swing.GuiFactory;
import delta.games.lotro.character.storage.CharacterStorage;
import delta.games.lotro.character.storage.bags.BagsManager;
import delta.games.lotro.character.storage.vaults.Vault;
import delta.games.lotro.gui.character.storage.StorageUiUtils;

/**
 * Controller for a panel that displays storage summary for a single character.
 * @author DAM
 */
public class StorageSummaryPanelController
{
  // Data
  private CharacterStorage _storage;
  // UI
  private JPanel _panel;
  private JProgressBar _vault;
  private JProgressBar _bags;

  /**
   * Constructor.
   */
  public StorageSummaryPanelController()
  {
    _panel=buildPanel();
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
   * Update values.
   * @param storage Storage to show.
   */
  public void update(CharacterStorage storage)
  {
    _storage=storage;
    // Bags
    {
      BagsManager bags=_storage.getBags();
      if (bags!=null)
      {
        Integer used=Integer.valueOf(bags.getUsed());
        Integer max=Integer.valueOf(bags.getCapacity());
        StorageUiUtils.updateProgressBar(_bags,used,max);
      }
      else
      {
        StorageUiUtils.updateProgressBar(_bags,null,null);
      }
    }
    // Vault
    {
      Vault ownVault=_storage.getOwnVault();
      if (ownVault!=null)
      {
        Integer used=Integer.valueOf(ownVault.getUsed());
        Integer max=Integer.valueOf(ownVault.getCapacity());
        StorageUiUtils.updateProgressBar(_vault,used,max);
      }
      else
      {
        StorageUiUtils.updateProgressBar(_vault,null,null);
      }
    }
  }

  private JPanel buildPanel()
  {
    JPanel panel=GuiFactory.buildPanel(new GridBagLayout());
    GridBagConstraints c=new GridBagConstraints(0,0,1,1,0.0,0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
    // Bags
    JLabel bagsLabel=GuiFactory.buildLabel("Bags:");
    panel.add(bagsLabel,c);
    c.gridx++;
    _bags=StorageUiUtils.buildProgressBar();
    panel.add(_bags,c);
    c.gridy++;c.gridx=0;
    // Vault
    JLabel vaultLabel=GuiFactory.buildLabel("Vault:");
    panel.add(vaultLabel,c);
    c.gridx++;
    _vault=StorageUiUtils.buildProgressBar();
    panel.add(_vault,c);
    TitledBorder border=GuiFactory.buildTitledBorder("Capacity");
    panel.setBorder(border);
    return panel;
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Data
    _storage=null;
    // UI
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
    _bags=null;
    _vault=null;
  }
}
