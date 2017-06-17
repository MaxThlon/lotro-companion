package delta.games.lotro.gui.stats.traitPoints;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharacterSummary;
import delta.games.lotro.character.log.LotroTestUtils;
import delta.games.lotro.stats.traitPoints.TraitPointsStatus;

/**
 * Test class for the trait points edition window.
 * @author DAM
 */
public class MainTestTraitPointsWindow
{
  private void doIt()
  {
    CharacterFile file=new LotroTestUtils().getToonByName("Meva");
    TraitPointsStatus status=new TraitPointsStatus();
    CharacterSummary summary=file.getSummary();
    TraitPointsEditionWindowController windowController=new TraitPointsEditionWindowController(null,summary,status);
    windowController.show(true);
    System.out.println(status);
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainTestTraitPointsWindow().doIt();
  }
}
