package delta.games.lotro.gui.common.stats;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import delta.common.ui.swing.GuiFactory;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatUtils;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Stats panel utilities.
 * @author DAM
 */
public class StatsPanel
{
  /**
   * Fill a panel with stats.
   * @param panel Panel to use.
   * @param stats Stats to show.
   * @param referenceStats Reference stats (to show a percentage).
   */
  public static void fillStatsPanel(JPanel panel, BasicStatsSet stats, BasicStatsSet referenceStats)
  {
    panel.removeAll();

    int rowIndex=0;
    GridBagConstraints strutConstraints=new GridBagConstraints(0,rowIndex,3,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0,5,0,0),0,0);
    panel.add(Box.createHorizontalStrut(100),strutConstraints);
    rowIndex++;

    int statsCount=stats.getStatsCount();
    if (statsCount>0)
    {
      // Build display
      for(StatDescription stat : stats.getSortedStats())
      {
        if (!stat.isVisible())
        {
          continue;
        }
        FixedDecimalsInteger value=stats.getStat(stat);
        if (value!=null)
        {
          // Value label
          String valueStr=StatUtils.getStatDisplay(value,stat.isPercentage());
          JLabel valueLabel=GuiFactory.buildLabel(valueStr);
          GridBagConstraints c=new GridBagConstraints(0,rowIndex,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0,5,0,0),0,0);
          panel.add(valueLabel,c);
          // Name label
          String name=stat.getName();
          JLabel statLabel=GuiFactory.buildLabel(name);
          c=new GridBagConstraints(1,rowIndex,1,1,1.0,0.0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(0,5,0,0),0,0);
          panel.add(statLabel,c);
          if (referenceStats!=null)
          {
            // Percentage
            FixedDecimalsInteger statValue=referenceStats.getStat(stat);
            String percentageStr="";
            if (statValue!=null)
            {
              float percentage=100*(value.floatValue()/statValue.floatValue());
              percentageStr=String.format("%.1f%%",Float.valueOf(percentage));
            }
            JLabel percentageLabel=GuiFactory.buildLabel(percentageStr);
            c=new GridBagConstraints(2,rowIndex,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0,5,0,5),0,0);
            panel.add(percentageLabel,c);
          }
          rowIndex++;
        }
      }
    }
  }
}
