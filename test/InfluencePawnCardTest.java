import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Card;
import model.InfluencePawnCard;
import model.InfluencePosition;
import model.InfluenceType;

/**
 * Class to test the behavior of the enhanced pawn card.
 */
public class InfluencePawnCardTest {

  private InfluencePosition pos1;
  private InfluencePosition pos2;
  private InfluencePosition pos3;
  private Map<InfluencePosition, InfluenceType> mixedInfluence;
  private InfluencePawnCard testCard;

  @Before
  public void setup() {
    pos1 = new InfluencePosition(1, 0);
    pos2 = new InfluencePosition(0, -1);
    pos3 = new InfluencePosition(-1, 1);

    mixedInfluence = new HashMap<>();
    mixedInfluence.put(pos1, InfluenceType.INFLUENCE);
    mixedInfluence.put(pos2, InfluenceType.UPGRADE);
    mixedInfluence.put(pos3, InfluenceType.DEVALUE);

    testCard = new InfluencePawnCard("Flame", 2, 4, mixedInfluence);
  }

  @Test
  public void testGetInfluenceOnlyReturnsInfluenceType() {
    List<InfluencePosition> influence = testCard.getInfluence();
    Assert.assertEquals(1, influence.size());
    Assert.assertTrue(influence.contains(pos1));
  }

  @Test
  public void testGetUpgradesOnlyReturnsUpgradeType() {
    List<InfluencePosition> upgrades = testCard.getUpgrades();
    Assert.assertEquals(1, upgrades.size());
    Assert.assertTrue(upgrades.contains(pos2));
  }

  @Test
  public void testGetDevaluesOnlyReturnsDevalueType() {
    List<InfluencePosition> devalues = testCard.getDevalues();
    Assert.assertEquals(1, devalues.size());
    Assert.assertTrue(devalues.contains(pos3));
  }

  @Test
  public void testMirrorInfluence() {
    Card mirrored = testCard.mirrorInfluence();
    Assert.assertTrue(mirrored instanceof InfluencePawnCard);

    List<InfluencePosition> mirroredInfluence = mirrored.getInfluence();
    Assert.assertTrue(mirroredInfluence.contains(new InfluencePosition(-1, 0)));

    List<InfluencePosition> mirroredUpgrades = mirrored.getUpgrades();
    Assert.assertTrue(mirroredUpgrades.contains(new InfluencePosition(0, -1)));

    List<InfluencePosition> mirroredDevalues = mirrored.getDevalues();
    Assert.assertTrue(mirroredDevalues.contains(new InfluencePosition(1, 1)));
  }

  @Test
  public void testEqualsAndHashCode() {
    InfluencePawnCard same = new InfluencePawnCard("Flame", 2, 4, new HashMap<>(mixedInfluence));
    InfluencePawnCard differentName = new InfluencePawnCard("Ice", 2, 4, mixedInfluence);
    InfluencePawnCard differentCost = new InfluencePawnCard("Flame", 1, 4, mixedInfluence);
    InfluencePawnCard differentValue = new InfluencePawnCard("Flame", 2, 3, mixedInfluence);

    Assert.assertEquals(testCard, same);
    Assert.assertNotEquals(testCard, differentName);
    Assert.assertNotEquals(testCard, differentCost);
    Assert.assertNotEquals(testCard, differentValue);

    Assert.assertEquals(testCard.hashCode(), same.hashCode());
  }
}
