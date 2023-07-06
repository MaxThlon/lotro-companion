package delta.games.lotro.gui.account.status.rewardsTracks.summary;

import javax.swing.JButton;
import javax.swing.JLabel;

import delta.common.ui.swing.GuiFactory;
import delta.games.lotro.account.status.rewardsTrack.RewardsTrackStatus;
import delta.games.lotro.lore.rewardsTrack.RewardsTrack;

/**
 * Controller for the UI items to display a single rewards track status summary.
 * @author DAM
 */
public class SingleRewardsTrackGadgetsController
{
  // Data
  private RewardsTrackStatus _status;
  // UI
  // - rewards track state
  private JLabel _state;
  // - details button
  private JButton _button;

  /**
   * Constructor.
   * @param status Rewards track status to use.
   */
  public SingleRewardsTrackGadgetsController(RewardsTrackStatus status)
  {
    _status=status;
    // State
    _state=GuiFactory.buildLabel("?");
    // Button
    _button=GuiFactory.buildButton("Details..."); // I18n
    // Init
    setRewardsTrackStatus(status);
  }

  /**
   * Get the managed rewards track status.
   * @return a rewards track status.
   */
  public RewardsTrackStatus getStatus()
  {
    return _status;
  }

  /**
   * Get the gadget for the state.
   * @return a label.
   */
  public JLabel getStateGadget()
  {
    return _state;
  }

  /**
   * Get the details button.
   * @return a button.
   */
  public JButton getDetailsButton()
  {
    return _button;
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // UI
    _state=null;
  }

  /**
   * Set the data to display.
   * @param status Status to display.
   */
  private void setRewardsTrackStatus(RewardsTrackStatus status)
  {
    // State
    String stateLabel=buildStateLabel(status);
    _state.setText(stateLabel);
  }

  private String buildStateLabel(RewardsTrackStatus status)
  {
    if (status==null)
    {
      return "Unknown";
    }
    int currentMilestone=status.getCurrentMilestone();
    if (currentMilestone==0)
    {
      return "Not Started"; // I18n
    }
    RewardsTrack rewardsTrack=status.getRewardsTrack();
    String claimedComplement="";
    int claimed=status.getClaimedMilestones();
    if (claimed<currentMilestone)
    {
      int toClaim=currentMilestone-claimed;
      claimedComplement=" ("+toClaim+" to claim"+")"; // I18n
    }
    int maxLevel=rewardsTrack.getSize();
    if (currentMilestone>=maxLevel)
    {
      int nbRepeats=currentMilestone-maxLevel+1;
      String complement=(nbRepeats>1)?" (last step x"+nbRepeats+")":""; // I18n
      return "Finished"+complement+claimedComplement; // I18n
    }
    return currentMilestone+" / "+maxLevel+claimedComplement;
  }
}
