package model;


import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

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
    assertEquals(1, influence.size());
    assertTrue(influence.contains(pos1));
  }

  @Test
  public void testGetUpgradesOnlyReturnsUpgradeType() {
    List<InfluencePosition> upgrades = testCard.getUpgrades();
    assertEquals(1, upgrades.size());
    assertTrue(upgrades.contains(pos2));
  }

  @Test
  public void testGetDevaluesOnlyReturnsDevalueType() {
    List<InfluencePosition> devalues = testCard.getDevalues();
    assertEquals(1, devalues.size());
    assertTrue(devalues.contains(pos3));
  }

  @Test
  public void testMirrorInfluence() {
    Card mirrored = testCard.mirrorInfluence();
    assertTrue(mirrored instanceof InfluencePawnCard);

    List<InfluencePosition> mirroredInfluence = mirrored.getInfluence();
    assertTrue(mirroredInfluence.contains(new InfluencePosition(-1, 0)));

    List<InfluencePosition> mirroredUpgrades = mirrored.getUpgrades();
    assertTrue(mirroredUpgrades.contains(new InfluencePosition(0, -1)));

    List<InfluencePosition> mirroredDevalues = mirrored.getDevalues();
    assertTrue(mirroredDevalues.contains(new InfluencePosition(1, 1)));
  }

  @Test
  public void testEqualsAndHashCode() {
    InfluencePawnCard same = new InfluencePawnCard("Flame", 2, 4, new HashMap<>(mixedInfluence));
    InfluencePawnCard differentName = new InfluencePawnCard("Ice", 2, 4, mixedInfluence);
    InfluencePawnCard differentCost = new InfluencePawnCard("Flame", 1, 4, mixedInfluence);
    InfluencePawnCard differentValue = new InfluencePawnCard("Flame", 2, 3, mixedInfluence);

    assertEquals(testCard, same);
    assertNotEquals(testCard, differentName);
    assertNotEquals(testCard, differentCost);
    assertNotEquals(testCard, differentValue);

    assertEquals(testCard.hashCode(), same.hashCode());
  }
}
