package delta.games.lotro.stats.deeds.statistics;

import java.util.Comparator;

/**
 * @author dm
 */
public class TitleEventNameComparator implements Comparator<TitleEvent>
{
  @Override
  public int compare(TitleEvent o1, TitleEvent o2)
  {
    String title1=o1.getTitle();
    String title2=o2.getTitle();
    return title1.compareTo(title2);
  }
}
