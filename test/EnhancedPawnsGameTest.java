import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.BoardCell;
import model.Card;
import model.EnhancedPawnsGame;
import model.InfluencePawnCard;
import model.InfluencePosition;
import model.InfluenceType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Class to test the behavior of the new enhanced model.
 */
public class EnhancedPawnsGameTest {

  EnhancedPawnsGame model;
  List<Card> redDeck;
  List<Card> blueDeck;

  @Before
  public void setup() {
    model = new EnhancedPawnsGame(3, 5);

    Map<InfluencePosition, InfluenceType> influence = new HashMap<>();
    influence.put(new InfluencePosition(0, 1), InfluenceType.INFLUENCE);
    influence.put(new InfluencePosition(1, 0), InfluenceType.UPGRADE);
    influence.put(new InfluencePosition(-1, 0), InfluenceType.DEVALUE);

    InfluencePawnCard testCard = new InfluencePawnCard("Boost", 1, 3, influence);
    redDeck = new ArrayList<>(Collections.nCopies(20, testCard));
    blueDeck = new ArrayList<>(Collections.nCopies(20, testCard));

    model.setupGame(new ArrayList<>(redDeck), new ArrayList<>(blueDeck), 5, false);
  }

  @Test
  public void testApplyCardInfluence_UpgradesAndDevalues() {
    // RED plays
    Card redCard = model.getCurrentPlayerHand().get(0);
    model.placeCard(1, 0, redCard);

    // BLUE plays
    Card blueCard = model.getCurrentPlayerHand().get(0);
    model.placeCard(1, 4, blueCard);

    BoardCell[][] board = model.getBoard();

    // The RED card at (1,0) places an additional pawn at (0,0)
    // there is already one pawn placed in this cell based on the start board configuration
    assertEquals(2, board[0][0].getPawns());

    // The RED card at (1,0) applies upgrade to (1,1)
    assertEquals(1, board[1][1].getUpgrade());

    // The RED card at (1,0) applies devalue to (1,-1) (which is off the board, skipped)

    // The BLUE card at (1,4) applies upgrade to (1,3)
    assertEquals(1, board[1][3].getUpgrade());

    // The BLUE card at (1,4) adds an additional pawn to (0,4)
    // there is already a pawn in this cell based on the starting board configuration
    assertEquals(2, board[0][4].getPawns());
  }

  @Test
  public void testCardRemovedWhenAdjustedValueIsZero() {
    Map<InfluencePosition, InfluenceType> onlyDevalue = new HashMap<>();
    onlyDevalue.put(new InfluencePosition(0, 1), InfluenceType.DEVALUE); // devalues above

    InfluencePawnCard weakCard = new InfluencePawnCard("Zero", 1, 1, onlyDevalue);
    List<Card> deck = new ArrayList<>(Collections.nCopies(20, weakCard));

    EnhancedPawnsGame game = new EnhancedPawnsGame(3, 5);
    game.setupGame(new ArrayList<>(deck), new ArrayList<>(deck), 5, false);

    // RED devalues (1,0)
    game.placeCard(2, 0, game.getCurrentPlayerHand().get(0)); // devalue hits 1,0
    game.placeCard(2, 4, game.getCurrentPlayerHand().get(0)); // BLUE
    // RED plays card with value 1 at (1,0) → adjusted score = 0
    game.placeCard(1, 0, game.getCurrentPlayerHand().get(0));
    game.placeCard(1, 4, game.getCurrentPlayerHand().get(0));


    game.getRowScores(1);

    BoardCell[][] board = game.getBoard();

    assertNull(board[1][0].getCard());
    assertNull(board[1][4].getCard());

    assertEquals(0, board[1][0].getDevalue());
    assertEquals(0, board[1][4].getDevalue());
  }



  @Test
  public void testScoreCalculationWithModifiers() {
    Card card = model.getCurrentPlayerHand().get(0);
    model.placeCard(1, 0, card); // RED plays
    model.placeCard(1, 4, model.getCurrentPlayerHand().get(0)); // BLUE plays

    int[] scores = model.getRowScores(1);
    assertEquals(3, scores[0]);
    assertEquals(3, scores[1]);
  }

  @Test
  public void testGameSetupAndDraw() {
    assertEquals(5, model.getCurrentPlayerHand().size());
    model.drawCard();
    assertEquals(6, model.getCurrentPlayerHand().size());
  }

  @Test
  public void testRowScoringWithUpgradeDevalue() {
    // Card is worth 3, upgrade +1, devalue -1 → still 3
    model.placeCard(1, 0, model.getCurrentPlayerHand().get(0));
    model.placeCard(1, 4, model.getCurrentPlayerHand().get(0));
    int[] scores = model.getRowScores(1);
    assertEquals(3, scores[0]);
    assertEquals(3, scores[1]);
  }


  @Test
  public void testSetupGameAndDrawCard() {
    assertEquals(5, model.getCurrentPlayerHand().size());
    model.drawCard();
    assertEquals(6, model.getCurrentPlayerHand().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardIllegalMove() {
    model.placeCard(-1, -1, model.getCurrentPlayerHand().get(0));
  }

  @Test
  public void testDetermineWinnerLogic() {
    // simulate passes
    model.passTurn();
    model.passTurn();

    assertTrue(model.isGameOver());
    assertNull(model.determineWinner()); // Tied by default
  }
}
