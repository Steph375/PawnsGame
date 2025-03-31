import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.List;

import model.Card;
import model.InfluencePosition;
import model.PawnCard;

/**
 * Tests the Pawn Card Class.
 */
public class PawnCardTest {

  private PawnCard card;

  @Before
  public void setUp() {
    HashMap<InfluencePosition, Boolean> influenceMap = new HashMap<>();
    influenceMap.put(new InfluencePosition(-1, 1), true);
    influenceMap.put(new InfluencePosition(0, 1), false);
    influenceMap.put(new InfluencePosition(1, 1), true);
    influenceMap.put(new InfluencePosition(-1, 0), true);
    influenceMap.put(new InfluencePosition(0, 0), false);
    influenceMap.put(new InfluencePosition(1, 0), true);
    influenceMap.put(new InfluencePosition(-1, -1), false);
    influenceMap.put(new InfluencePosition(0, -1), true);
    influenceMap.put(new InfluencePosition(1, -1), false);

    card = new PawnCard("TestCard", 2, 3, influenceMap);
  }

  @Test
  public void testConstructorValid() {
    // A valid PawnCard should be constructed without exceptions.
    PawnCard c = new PawnCard("Valid", 1, 1, new HashMap<InfluencePosition, Boolean>());
    Assert.assertEquals("Valid", c.getName());
    Assert.assertEquals(1, c.getCost());
    Assert.assertEquals(1, c.getValueScore());
  }

  @Test
  public void testConstructorInvalidCost() {
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new PawnCard("Invalid", 0, 1, new HashMap<>());
    });
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new PawnCard("Invalid", 4, 1, new HashMap<>());
    });
  }

  @Test
  public void testConstructorInvalidValueScore() {
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new PawnCard("Invalid", 2, 0, new HashMap<>());
    });
  }

  @Test
  public void testGetNameCostValue() {
    Assert.assertEquals("TestCard", card.getName());
    Assert.assertEquals(2, card.getCost());
    Assert.assertEquals(3, card.getValueScore());
  }

  @Test
  public void testGetInfluence() {
    List<InfluencePosition> influences = card.getInfluence();
    // Based on our influenceMap, only positions with value true should be returned:
    // expected: (-1,1), (1,1), (-1,0), (1,0), (0,-1)
    List<InfluencePosition> expected = new ArrayList<>();
    expected.add(new InfluencePosition(-1, 1));
    expected.add(new InfluencePosition(1, 1));
    expected.add(new InfluencePosition(-1, 0));
    expected.add(new InfluencePosition(1, 0));
    expected.add(new InfluencePosition(0, -1));

    Assert.assertEquals(expected.size(), influences.size());
    for (InfluencePosition pos : expected) {
      Assert.assertTrue(influences.contains(pos));
    }
  }

  @Test
  public void testMirrorInfluence() {
    // Call mirrorInfluence to produce a mirrored card.
    Card mirrored = card.mirrorInfluence();
    Assert.assertEquals(card.getName(), mirrored.getName());
    Assert.assertEquals(card.getCost(), mirrored.getCost());
    Assert.assertEquals(card.getValueScore(), mirrored.getValueScore());

    // The influence positions should have their x-coordinates negated.
    List<InfluencePosition> originalInfluences = card.getInfluence();
    List<InfluencePosition> mirroredInfluences = mirrored.getInfluence();
    for (InfluencePosition pos : originalInfluences) {
      InfluencePosition expectedMirror = new InfluencePosition(-pos.getX(), pos.getY());
      Assert.assertTrue(mirroredInfluences.contains(expectedMirror));
    }
    Assert.assertEquals(originalInfluences.size(), mirroredInfluences.size());
  }
}
