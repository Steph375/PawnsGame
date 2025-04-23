import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import model.Card;
import model.EnhancedCell;
import model.PawnCard;
import model.PlayerColor;

/**
 * Test class for enhanced cell.
 */
public class EnhancedCellTest {
  EnhancedCell cell;
  Card testCard;

  @Before
  public void setup() {
    testCard = new PawnCard("Test", 1, 2, Map.of());
    cell = new EnhancedCell(2, null, PlayerColor.RED);
  }

  @Test
  public void testInitialModifiers() {
    Assert.assertEquals(0, cell.getUpgrade());
    Assert.assertEquals(0, cell.getDevalue());
  }

  @Test
  public void testApplyUpgrade() {
    cell.applyUpgrade();
    cell.applyUpgrade();
    Assert.assertEquals(2, cell.getUpgrade());
  }

  @Test
  public void testApplyDevalue() {
    cell.applyDevalue();
    cell.applyDevalue();
    cell.applyDevalue();
    Assert.assertEquals(3, cell.getDevalue());
  }

  @Test
  public void testResetModifiers() {
    cell.applyUpgrade();
    cell.applyDevalue();
    cell.resetModifiers();
    Assert.assertEquals(0, cell.getUpgrade());
    Assert.assertEquals(0, cell.getDevalue());
  }

  @Test
  public void testInheritedAddPawnsSameOwner() {
    cell.addPawns(1, PlayerColor.RED);
    Assert.assertEquals(3, cell.getPawns());
    Assert.assertEquals(PlayerColor.RED, cell.getColor());
  }

  @Test
  public void testInheritedAddPawnsDifferentOwner() {
    cell.addPawns(2, PlayerColor.BLUE);
    Assert.assertEquals(2, cell.getPawns()); // doesn't increase
    Assert.assertEquals(PlayerColor.BLUE, cell.getColor()); // ownership changes
  }

  @Test
  public void testInheritedPlaceCard() {
    cell.placeCard(testCard, PlayerColor.RED);
    Assert.assertEquals(testCard, cell.getCard());
    Assert.assertEquals(PlayerColor.RED, cell.getColor());
  }

  @Test
  public void testClearCardRestoresPawns() {
    EnhancedCell cardCell = new EnhancedCell(2, testCard, PlayerColor.RED);
    cardCell.clearCard();
    Assert.assertNull(cardCell.getCard());
    Assert.assertEquals(testCard.getCost(), cardCell.getPawns());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceNullCardThrows() {
    cell.placeCard(null, PlayerColor.RED);
  }
}
