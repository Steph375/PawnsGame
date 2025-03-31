import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import model.Card;
import model.Cell;
import model.InfluencePosition;
import model.PawnCard;
import model.PlayerColor;

/**
 * A Class to test the public behavior of the Cell Class.
 */
public class CellTest {

  Cell emptyCell;
  Cell startRedCell;
  Cell threePawnBlueCell;
  Cell twoPawnRedCell;
  Card dummyCard;

  @Before
  public void setUp() {
    emptyCell = new Cell(0, null, null);
    startRedCell = new Cell(1, null, PlayerColor.RED);
    threePawnBlueCell = new Cell(3, null, PlayerColor.BLUE);
    twoPawnRedCell = new Cell(2, null, PlayerColor.RED);

    dummyCard = new PawnCard("Dummy", 1, 2, new HashMap<InfluencePosition, Boolean>());
  }

  @Test
  public void testConstructionExceptions() {
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new Cell(-1, null, PlayerColor.RED);
    });
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new Cell(4, null, PlayerColor.BLUE);
    });

    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new Cell(1, null, null);
    });
  }

  @Test
  public void testGetCard() {
    Assert.assertNull(emptyCell.getCard());
    Cell cell = new Cell(1, null, PlayerColor.RED);
    cell.placeCard(dummyCard, PlayerColor.RED);
    Assert.assertEquals(dummyCard, cell.getCard());
  }

  @Test
  public void testGetPawns() {
    Assert.assertEquals(0, emptyCell.getPawns());
    Assert.assertEquals(1, startRedCell.getPawns());
    Assert.assertEquals(3, threePawnBlueCell.getPawns());
    Assert.assertEquals(2, twoPawnRedCell.getPawns());
  }

  @Test
  public void testGetColor() {
    Assert.assertNull(emptyCell.getColor());
    Assert.assertEquals(PlayerColor.RED, startRedCell.getColor());
    Assert.assertEquals(PlayerColor.BLUE, threePawnBlueCell.getColor());
  }

  @Test
  public void testAddPawns() {
    // Test add 2 red pawns.
    Assert.assertEquals(0, emptyCell.getPawns());
    Assert.assertNull(emptyCell.getColor());
    emptyCell.addPawns(2, PlayerColor.RED);
    Assert.assertEquals(2, emptyCell.getPawns());
    Assert.assertEquals(PlayerColor.RED, emptyCell.getColor());

    // Test same owner
    Assert.assertEquals(2, twoPawnRedCell.getPawns());
    Assert.assertEquals(PlayerColor.RED, twoPawnRedCell.getColor());
    twoPawnRedCell.addPawns(1, PlayerColor.RED);
    Assert.assertEquals(3, twoPawnRedCell.getPawns());
    Assert.assertEquals(PlayerColor.RED, twoPawnRedCell.getColor());

    // Test different owner
    Assert.assertEquals(3, threePawnBlueCell.getPawns());
    Assert.assertEquals(PlayerColor.BLUE, threePawnBlueCell.getColor());
    // Adding a pawn from a different owner should change the owner, but not increase pawn count.
    threePawnBlueCell.addPawns(1, PlayerColor.RED);
    Assert.assertEquals(3, threePawnBlueCell.getPawns());
    Assert.assertEquals(PlayerColor.RED, threePawnBlueCell.getColor());
  }

  @Test
  public void testPlaceCard() {
    // Create a cell with 1 pawn and a known owner.
    Cell cell = new Cell(1, null, PlayerColor.RED);
    cell.placeCard(dummyCard, PlayerColor.RED);
    // After placing a card, getCard should return
    // dummyCard and getColor should remain as the owner's color.
    Assert.assertEquals(dummyCard, cell.getCard());
    Assert.assertEquals(PlayerColor.RED, cell.getColor());
  }

  @Test
  public void testPlaceCardChangesColor() {
    // RED.
    Cell cell = new Cell(1, null, PlayerColor.RED);
    // Now BLUE
    cell.placeCard(dummyCard, PlayerColor.BLUE);
    Assert.assertEquals(dummyCard, cell.getCard());
    Assert.assertEquals(PlayerColor.BLUE, cell.getColor());
  }

  @Test
  public void testRemoveAllPawns() {
    Assert.assertEquals(1, startRedCell.getPawns());
    startRedCell.removeAllPawns();
    Assert.assertEquals(0, startRedCell.getPawns());
    // The color remains unchanged.
    Assert.assertEquals(PlayerColor.RED, startRedCell.getColor());
  }
}
