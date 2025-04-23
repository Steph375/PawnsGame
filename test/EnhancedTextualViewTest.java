import model.Card;
import model.EnhancedPawnsGame;
import model.InfluencePawnCard;
import model.InfluencePosition;
import model.InfluenceType;
import model.PawnCard;
import model.PawnsGame;
import view.EnhancedTextualView;
import view.TextualView;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Class to test that the EnhancedTextualView visualizes the model properly.
 */
public class EnhancedTextualViewTest {

  private PawnsGame model;
  private List<Card> redDeck;
  private List<Card> blueDeck;
  private TextualView view;

  @Before
  public void setUp() {
    model = new EnhancedPawnsGame(3, 5);

    Map<InfluencePosition, InfluenceType> redInfluence = new HashMap<>();
    redInfluence.put(new InfluencePosition(0, 1), InfluenceType.INFLUENCE);  // (1,1)
    redInfluence.put(new InfluencePosition(1, 0), InfluenceType.UPGRADE);   // (2,0)
    redInfluence.put(new InfluencePosition(2, 0), InfluenceType.DEVALUE);   // (3,0) - off board

    Map<InfluencePosition, InfluenceType> blueInfluence = new HashMap<>();
    blueInfluence.put(new InfluencePosition(0, 1), InfluenceType.INFLUENCE);   // (1,5)
    blueInfluence.put(new InfluencePosition(-1, 0), InfluenceType.UPGRADE);    // (0,4)
    blueInfluence.put(new InfluencePosition(-2, 0), InfluenceType.DEVALUE);    // (-1,4) - off board

    InfluencePawnCard redCard = new InfluencePawnCard("Red", 1, 3, redInfluence);
    InfluencePawnCard blueCard = new InfluencePawnCard("Blue", 1, 3, blueInfluence);

    redDeck = new ArrayList<>(Collections.nCopies(20, redCard));
    blueDeck = new ArrayList<>(Collections.nCopies(20, blueCard));

    model.setupGame(new ArrayList<>(redDeck), new ArrayList<>(blueDeck), 5, false);
    view = new EnhancedTextualView(model);
  }

  @Test
  public void testTextualRenderingWithInfluences() {
    // Initial view
    assertEquals(
            "0 1 _ _ _ 1  0\n" +
                    "0 1 _ _ _ 1  0\n" +
                    "0 1 _ _ _ 1  0", view.toString());

    model.placeCard(1, 0, redDeck.get(0));

    assertEquals(
            "0 2 _ _ _ 1  0\n" +
                    "3 R+0 +1 -1 _ 1  0\n" +
                    "0 1 _ _ _ 1  0", view.toString());

    model.placeCard(1, 4, blueDeck.get(0));

    assertEquals(
            "0 2 _ _ _ 2  0\n" +
                    "3 R+0 +1 -2 +1 B+0  3\n" +
                    "0 1 _ _ _ 1  0", view.toString());

    // Extra test with a non-enhanced card
    Map<InfluencePosition, InfluenceType> belowRight = new HashMap<>();
    belowRight.put(new InfluencePosition(1, -1), InfluenceType.DEVALUE);   // (1,-1)
    belowRight.put(new InfluencePosition(2, -1), InfluenceType.INFLUENCE); // (2,-1)

    model.placeCard(0, 0, new PawnCard("Test", 1, 2, belowRight));

    assertEquals(
            "2 R+0 _ _ _ 2  0\n" +
                    "3 R+0 +1 1-2 +1 B+0  3\n" +
                    "0 1 _ _ _ 1  0", view.toString());
  }
}
