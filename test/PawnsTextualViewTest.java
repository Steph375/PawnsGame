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
import model.PawnsGameModel;
import view.PawnsTextualView;

/**
 * Test the PawnTextualView Class.
 */
public class PawnsTextualViewTest {

  String path;
  File configFile;
  List<Card> sampleDeck;
  PawnsGameModel threeByFiveModel;
  PawnsTextualView view;

  HashMap<InfluencePosition, Boolean> securityInfluence;
  PawnCard security;

  @Before
  public void setUp() {
    path = "docs" + File.separator + "deck.config";
    configFile = new File(path);
    sampleDeck = DeckReader.readDeck(configFile);
    threeByFiveModel = new PawnsGameModel(3, 5);
    view = new PawnsTextualView(threeByFiveModel);

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

    security = new PawnCard("Security", 1, 2, securityInfluence);

  }

  @Test
  public void testToString() {
    Assert.assertEquals("0 1___1 0\n" +
            "0 1___1 0\n" +
            "0 1___1 0", view.toString());

    threeByFiveModel.setupGame(new ArrayList<Card>(sampleDeck),
            new ArrayList<Card>(sampleDeck), 5, false);

    Assert.assertEquals("0 1___1 0\n" +
            "0 1___1 0\n" +
            "0 1___1 0", view.toString());

    threeByFiveModel.placeCard(0, 0, security);

    Assert.assertEquals("2 R1__1 0\n" +
            "0 2___1 0\n" +
            "0 1___1 0", view.toString());

    threeByFiveModel.placeCard(1, 4, security);

    Assert.assertEquals("2 R1__2 0\n" +
            "0 2__1B 2\n" +
            "0 1___2 0", view.toString());
  }
}