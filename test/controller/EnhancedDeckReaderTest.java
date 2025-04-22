package controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;


import model.Card;
import model.InfluencePosition;

/**
 * Tests to ensure proper function of enhanced deck reader
 */
public class EnhancedDeckReaderTest {

  List<Card> enhancedDeck;
  Card flame;
  Card frost;

  @Before
  public void setUp() {
    String path = "docs" + File.separator + "enhanced.config"; // your updated deck file
    File configFile = new File(path);
    enhancedDeck = EnhancedDeckReader.readDeck(configFile);

    flame =  enhancedDeck.get(0); // assuming Flame is first
    frost = enhancedDeck.get(1); // assuming Frost is second
  }

  @Test
  public void testFlameCardInfluenceTypes() {
    List<InfluencePosition> flameInfluence = flame.getInfluence();
    System.out.println(flameInfluence);
    List<InfluencePosition> flameUpgrades = flame.getUpgrades();
    System.out.println(flameUpgrades);
    List<InfluencePosition> flameDevalues = flame.getDevalues();
    System.out.println(flameDevalues);

    // check counts: at least 3 total
    int total = flameInfluence.size() + flameUpgrades.size() + flameDevalues.size();
    Assert.assertTrue(total >= 3);



    // Upgrades or Devalues must contain at least one
    Assert.assertTrue(!flameUpgrades.isEmpty() || !flameDevalues.isEmpty());
  }

  @Test
  public void testFrostCardInfluenceTypes() {
    List<InfluencePosition> frostInfluence = frost.getInfluence();
    List<InfluencePosition> frostUpgrades = frost.getUpgrades();
    List<InfluencePosition> frostDevalues = frost.getDevalues();

    int total = frostInfluence.size() + frostUpgrades.size() + frostDevalues.size();
    Assert.assertTrue(total >= 3);

    // Make sure it’s not all just basic influence
    Assert.assertTrue(!frostUpgrades.isEmpty() || !frostDevalues.isEmpty());
  }
}
