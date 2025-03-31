import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.DeckReader;
import model.Card;
import model.InfluencePosition;
import model.PawnCard;

/**
 * Tests the controller.DeckReader Class.
 */
public class DeckReaderTest {

  String path;
  File configFile;
  List<Card> sampleDeck;
  HashMap<InfluencePosition, Boolean> securityInfluence;
  HashMap<InfluencePosition, Boolean> beeInfluence;
  PawnCard security;
  PawnCard bee;


  @Before
  public void setUp() {
    path = "docs" + File.separator + "deck.config";
    configFile = new File(path);
    sampleDeck = DeckReader.readDeck(configFile);

    securityInfluence = new HashMap<>();
    securityInfluence.put(new InfluencePosition(-2, 2), false);
    securityInfluence.put(new InfluencePosition(-1, 2), false);
    securityInfluence.put(new InfluencePosition(0, 2), false);
    securityInfluence.put(new InfluencePosition(1, 2), false);
    securityInfluence.put(new InfluencePosition(2, 2), false);
    securityInfluence.put(new InfluencePosition(-2, 1), false);
    securityInfluence.put(new InfluencePosition(-1, 1), false);
    securityInfluence.put(new InfluencePosition(0, 1), true);
    securityInfluence.put(new InfluencePosition(1, 1), false);
    securityInfluence.put(new InfluencePosition(2, 1), false);
    securityInfluence.put(new InfluencePosition(-2, 0), false);
    securityInfluence.put(new InfluencePosition(-1, 0), true);
    securityInfluence.put(new InfluencePosition(0, 0), false);
    securityInfluence.put(new InfluencePosition(1, 0), true);
    securityInfluence.put(new InfluencePosition(2, 0), false);
    securityInfluence.put(new InfluencePosition(-2, -1), false);
    securityInfluence.put(new InfluencePosition(-1, -1), false);
    securityInfluence.put(new InfluencePosition(0, -1), true);
    securityInfluence.put(new InfluencePosition(1, -1), false);
    securityInfluence.put(new InfluencePosition(2, -1), false);
    securityInfluence.put(new InfluencePosition(-2, -2), false);
    securityInfluence.put(new InfluencePosition(-1, -2), false);
    securityInfluence.put(new InfluencePosition(0, -2), false);
    securityInfluence.put(new InfluencePosition(1, -2), false);
    securityInfluence.put(new InfluencePosition(2, -2), false);

    beeInfluence = new HashMap<>();
    beeInfluence.put(new InfluencePosition(-2, 2), false);
    beeInfluence.put(new InfluencePosition(-1, 2), false);
    beeInfluence.put(new InfluencePosition(0, 2), false);
    beeInfluence.put(new InfluencePosition(1, 2), false);
    beeInfluence.put(new InfluencePosition(2, 2), false);
    beeInfluence.put(new InfluencePosition(-2, 1), false);
    beeInfluence.put(new InfluencePosition(-1, 1), true);
    beeInfluence.put(new InfluencePosition(0, 1), true);
    beeInfluence.put(new InfluencePosition(1, 1), false);
    beeInfluence.put(new InfluencePosition(2, 1), false);
    beeInfluence.put(new InfluencePosition(-2, 0), false);
    beeInfluence.put(new InfluencePosition(-1, 0), true);
    beeInfluence.put(new InfluencePosition(0, 0), false);
    beeInfluence.put(new InfluencePosition(1, 0), true);
    beeInfluence.put(new InfluencePosition(2, 0), false);
    beeInfluence.put(new InfluencePosition(-2, -1), false);
    beeInfluence.put(new InfluencePosition(-1, -1), true);
    beeInfluence.put(new InfluencePosition(0, -1), true);
    beeInfluence.put(new InfluencePosition(1, -1), false);
    beeInfluence.put(new InfluencePosition(2, -1), false);
    beeInfluence.put(new InfluencePosition(-2, -2), false);
    beeInfluence.put(new InfluencePosition(-1, -2), false);
    beeInfluence.put(new InfluencePosition(0, -2), false);
    beeInfluence.put(new InfluencePosition(1, -2), false);
    beeInfluence.put(new InfluencePosition(2, -2), false);

    security = new PawnCard("Security", 1, 2, securityInfluence);
    bee = new PawnCard("Bee", 2, 3, beeInfluence);
  }

  @Test
  public void testDeckCreation() {
    Assert.assertEquals(security, sampleDeck.get(0));
    Assert.assertEquals(bee, sampleDeck.get(1));
  }

  @Test
  public void testDeckReaderExceptions() {
    // exception if deck has more than two of the same card
    String threeKind = "test" + File.separator + "testConfigFiles" + File.separator
            + "ThreeOfAKindDeck.txt";
    File threeKindFile = new File(threeKind);
    Assert.assertThrows("Card Security appears more than twice in the deck.",
        IllegalArgumentException.class, () -> {
          DeckReader.readDeck(threeKindFile);
        });

    // IllegalArgumentException if the file isn't found
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      DeckReader.readDeck(new File("not a path"));
    });

    // returns an empty deck if the file is empty
    String emptyFilePath = "test" + File.separator + "testConfigFiles" + File.separator
            + "EmptyFile.txt";
    File emptyFile = new File(emptyFilePath);
    Assert.assertEquals(new ArrayList<Card>(), DeckReader.readDeck(emptyFile));

    // throw IllegalArgumentException if invalid header
    String invalidHeader = "test" + File.separator + "testConfigFiles" + File.separator
            + "InvalidHeader.txt";
    File invalidHeaderFile = new File(invalidHeader);
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      DeckReader.readDeck(invalidHeaderFile);
    });

    // throw IllegalArgumentException if cost/value are not integers
    String invalidCost = "test" + File.separator + "testConfigFiles" + File.separator
            + "InvalidCostValue.txt";
    File invalidCostFile = new File(invalidCost);
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      DeckReader.readDeck(invalidCostFile);
    });

    // throw IllegalArgumentException if the board representing influence is not correctly formatted
    String invalidInfluence = "test" + File.separator + "testConfigFiles" + File.separator
            + "InvalidInfluence.txt";
    File invalidInfluenceFile = new File(invalidInfluence);
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      DeckReader.readDeck(invalidInfluenceFile);
    });
  }
}