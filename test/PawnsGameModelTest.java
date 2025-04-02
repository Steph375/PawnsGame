import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.DeckReader;
import model.BoardCell;
import model.Card;
import model.IPlayer;
import model.InfluencePosition;
import model.PawnCard;
import model.PawnsGameModel;
import model.Player;
import model.PlayerColor;

/**
 * A class to test the public behavior of a PawnsGame Model.
 */
public class PawnsGameModelTest {
  String path;
  File configFile;
  List<Card> sampleDeck;
  PawnsGameModel threeByFiveModel;

  @Before
  public void setUp() {
    path = "docs" + File.separator + "deck.config";
    configFile = new File(path);
    sampleDeck = DeckReader.readDeck(configFile);
    threeByFiveModel = new PawnsGameModel(3, 5);
  }

  @Test
  public void testModelConstruction() {
    // column not positive
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new PawnsGameModel(1, 0);
    });

    // column not odd
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new PawnsGameModel(1, 2);
    });

    // row not positive
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new PawnsGameModel(0, 3);
    });
  }

  @Test
  public void testBoardInitialization() {

    BoardCell[][] board = threeByFiveModel.getBoard();

    Assert.assertEquals(3, board.length);
    Assert.assertEquals(5, board[0].length);
    for (int r = 0; r < board.length; r++) {
      Assert.assertEquals(1, board[r][0].getPawns());
      Assert.assertEquals(PlayerColor.RED, board[r][0].getColor());
      Assert.assertEquals(1, board[r][4].getPawns());
      Assert.assertEquals(PlayerColor.BLUE, board[r][4].getColor());
      for (int c = 1; c < 4; c++) {
        Assert.assertEquals(0, board[r][c].getPawns());
        Assert.assertNull(board[r][c].getColor());
      }
    }
  }

  @Test
  public void testSetupGameExceptions() {
    // Exception if first deck is null.
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      threeByFiveModel.setupGame(null, new ArrayList<Card>(sampleDeck), 5, false);
    });
    // Exception if second deck is null.
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      threeByFiveModel.setupGame(new ArrayList<>(sampleDeck), null, 5, false);
    });
    int requiredCards = threeByFiveModel.getHeight() * threeByFiveModel.getWidth() + 5;

    List<Card> deck1 = new ArrayList<>();
    List<Card> deck2 = new ArrayList<>();

    // Exception if first deck is too small.
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      threeByFiveModel.setupGame(deck1, new ArrayList<Card>(sampleDeck), 5, false);
    });
    // Exception if second deck too small.
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      threeByFiveModel.setupGame(new ArrayList<Card>(sampleDeck), deck2, 5, false);
    });

    // Exception if hand size <= 0.
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      threeByFiveModel.setupGame(new ArrayList<Card>(sampleDeck),
              new ArrayList<Card>(sampleDeck), 0, false);
    });
    // Exception if hand size greater than one-third of deck size.
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      threeByFiveModel.setupGame(new ArrayList<Card>(sampleDeck),
              new ArrayList<Card>(sampleDeck), 55, false);
    });

    // Start game successfully.
    this.threeByFiveModel.setupGame(new ArrayList<Card>(sampleDeck),
            new ArrayList<Card>(sampleDeck), 5, false);
    IPlayer playerRed = threeByFiveModel.getPlayerRed();
    IPlayer playerBlue = threeByFiveModel.getPlayerBlue();
    Assert.assertNotNull(playerRed);
    Assert.assertNotNull(playerBlue);
    Assert.assertEquals(PlayerColor.RED, playerRed.getColor());
    Assert.assertEquals(PlayerColor.BLUE, playerBlue.getColor());
    // throw exception if game already started
    Assert.assertThrows(IllegalStateException.class, () -> {
      this.threeByFiveModel.setupGame(new ArrayList<Card>(sampleDeck),
              new ArrayList<Card>(sampleDeck), 5, false);
    });

  }


  @Test
  public void testPlaceCardExceptions() {
    Card sampleCard = sampleDeck.get(0);

    // If game not started, placing a card should throw IllegalStateException.
    Assert.assertThrows(IllegalStateException.class, () -> {
      threeByFiveModel.placeCard(1, 1, sampleCard);
    });

    // Start game.
    threeByFiveModel.setupGame(new ArrayList<Card>(sampleDeck),
            new ArrayList<Card>(sampleDeck), 5, false);
    Assert.assertEquals(0, threeByFiveModel.getPasses());
    // placing a card in a cell with 0 pawns.
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      threeByFiveModel.placeCard(1, 2, sampleCard);
    });

    //Illegal move: not enough pawns to cover card cost.
    HashMap<InfluencePosition, Boolean> influence = new HashMap<>();
    PawnCard highCostCard = new PawnCard("HighCost", 2, 3, influence);

    Assert.assertThrows(IllegalArgumentException.class, () -> {
      threeByFiveModel.placeCard(1, 0, highCostCard);
    });

    // Legal move: For red, a cell in the first column is legal for a card with cost 1.
    if (threeByFiveModel.getCurrentPlayer() == PlayerColor.RED) {
      threeByFiveModel.placeCard(1, 0, sampleCard);

      Assert.assertThrows(IllegalArgumentException.class, () -> {
        threeByFiveModel.placeCard(1, 0, sampleCard);
      });
    }

    //  not player's turn.
    if (threeByFiveModel.getCurrentPlayer() == PlayerColor.RED) {
      Assert.assertThrows(IllegalArgumentException.class, () -> {
        threeByFiveModel.placeCard(1, 4, sampleCard);
      });
    } else {
      Assert.assertThrows(IllegalArgumentException.class, () -> {
        threeByFiveModel.placeCard(1, 0, sampleCard);
      });
    }

    Assert.assertThrows(IllegalArgumentException.class, () -> {
      threeByFiveModel.placeCard(1, 4, null);
    });
  }

  @Test
  public void testPlaceCard() {
    threeByFiveModel.setupGame(new ArrayList<>(sampleDeck),
            new ArrayList<>(sampleDeck), 5, false);
    List<Card> handBefore = threeByFiveModel.getCurrentPlayerHand();

    Card cardToPlace = handBefore.get(0);
    int targetRow = 1;
    int targetCol = 0;
    this.threeByFiveModel.placeCard(targetRow, targetCol, cardToPlace);
    Assert.assertEquals(PlayerColor.BLUE, threeByFiveModel.getCurrentPlayer());
    Assert.assertEquals(cardToPlace, threeByFiveModel.getBoard()[targetRow][targetCol].getCard());


    Assert.assertEquals(0, threeByFiveModel.getBoard()[targetRow][targetCol].getPawns());

    boolean influenceApplied = false;
    for (InfluencePosition pos : cardToPlace.getInfluence()) {
      int col = targetCol + pos.getX();
      int row = targetRow - pos.getY();
      if (row >= 0 && row < 3 && col >= 0 && col < 5) {
        if (threeByFiveModel.getBoard()[row][col].getPawns() > 0) {
          influenceApplied = true;
          break;
        }
      }
    }
    Assert.assertTrue(influenceApplied);

    Assert.assertNotEquals(PlayerColor.RED, threeByFiveModel.getCurrentPlayer());
  }

  @Test
  public void testPassTurn() {
    PawnsGameModel model = new PawnsGameModel(3, 5);
    //game not started
    Assert.assertThrows(IllegalStateException.class, () -> {
      model.passTurn();
    });

    model.setupGame(new ArrayList<>(sampleDeck), new ArrayList<>(sampleDeck),
            5, false);


    PlayerColor initialTurn = model.getCurrentPlayer();
    model.passTurn();
    Assert.assertEquals(1, model.getPasses());
    Assert.assertNotEquals(initialTurn, model.getCurrentPlayer());

    model.passTurn();
    Assert.assertEquals(2, model.getPasses());
    Assert.assertTrue(model.isGameOver());
  }

  @Test
  public void testDrawCard() {
    // If game not started drawCard should throw IllegalStateException.
    Assert.assertThrows(IllegalStateException.class, () -> {
      threeByFiveModel.drawCard();
    });

    threeByFiveModel.setupGame(new ArrayList<>(sampleDeck), new ArrayList<>(sampleDeck),
            5, false);

    IPlayer currentPlayer;
    if (threeByFiveModel.getCurrentPlayer() == PlayerColor.RED) {
      currentPlayer = threeByFiveModel.getPlayerRed();
    } else {
      currentPlayer = threeByFiveModel.getPlayerBlue();
    }
    int initialHandSize = threeByFiveModel.getCurrentPlayerHand().size();
    int initialDeckSize = ((Player) currentPlayer).getDeckSize();

    Card expectedDrawnCard = currentPlayer.getDeck().get(0);
    threeByFiveModel.drawCard();
    Assert.assertEquals(initialHandSize + 1, threeByFiveModel.getCurrentPlayerHand().size());
    Assert.assertEquals(initialDeckSize - 1, ((Player) currentPlayer).getDeckSize());
    Assert.assertTrue(threeByFiveModel.getCurrentPlayerHand().contains(expectedDrawnCard));
  }

  @Test
  public void testGetCurrentPlayer() {
    threeByFiveModel.setupGame(new ArrayList<Card>(sampleDeck), new ArrayList<Card>(sampleDeck),
            5, false);
    Assert.assertNotNull(threeByFiveModel.getCurrentPlayer());
    Assert.assertEquals(PlayerColor.RED, threeByFiveModel.getCurrentPlayer());
  }

  @Test
  public void testGetCurrentPlayerHand() {
    threeByFiveModel.setupGame(new ArrayList<Card>(sampleDeck), new ArrayList<Card>(sampleDeck),
            5, false);
    List<Card> hand = threeByFiveModel.getCurrentPlayerHand();
    Assert.assertNotNull(hand);
    Assert.assertEquals(5, hand.size());
  }

  @Test
  public void testCalculateTotalScore() {
    HashMap<InfluencePosition, Boolean> emptyInfluence = new HashMap<>();
    PawnCard redHigh = new PawnCard("RedHigh", 1, 5, emptyInfluence);
    PawnCard redLow = new PawnCard("RedLow", 1, 2, emptyInfluence);
    PawnCard redMed = new PawnCard("RedMed", 1, 3, emptyInfluence);

    PawnCard blueLow = new PawnCard("BlueLow", 1, 2, emptyInfluence);
    PawnCard blueHigh = new PawnCard("BlueHigh", 1, 4, emptyInfluence);
    PawnCard blueMed = new PawnCard("BlueMed", 1, 3, emptyInfluence);


    List<Card> redDeck = new ArrayList<>();
    redDeck.add(redHigh);
    redDeck.add(redLow);
    redDeck.add(redMed);
    redDeck.add(redHigh);
    redDeck.add(redLow);
    redDeck.add(redMed);
    redDeck.add(redHigh);
    redDeck.add(redLow);
    redDeck.add(redMed);
    redDeck.add(redHigh);
    redDeck.add(redLow);
    redDeck.add(redMed);
    redDeck.add(redHigh);
    redDeck.add(redLow);
    redDeck.add(redMed);
    redDeck.add(redHigh);
    redDeck.add(redLow);
    redDeck.add(redMed);


    List<Card> blueDeck = new ArrayList<>();
    blueDeck.add(blueLow);
    blueDeck.add(blueHigh);
    blueDeck.add(blueMed);
    blueDeck.add(blueLow);
    blueDeck.add(blueHigh);
    blueDeck.add(blueMed);
    blueDeck.add(blueLow);
    blueDeck.add(blueHigh);
    blueDeck.add(blueMed);
    blueDeck.add(blueLow);
    blueDeck.add(blueHigh);
    blueDeck.add(blueMed);
    blueDeck.add(blueLow);
    blueDeck.add(blueHigh);
    blueDeck.add(blueMed);
    blueDeck.add(blueLow);
    blueDeck.add(blueHigh);
    blueDeck.add(blueMed);


    PawnsGameModel model = new PawnsGameModel(3, 5);

    model.setupGame(redDeck, blueDeck, 3, false);


    List<Card> redHand = model.getCurrentPlayerHand();
    Card redCardRow0 = redHand.get(0);

    model.placeCard(0, 0, redCardRow0);


    List<Card> blueHand = model.getCurrentPlayerHand();
    Card blueCardRow0 = blueHand.get(0);

    model.placeCard(0, 4, blueCardRow0);


    redHand = model.getCurrentPlayerHand();
    PawnCard redCardRow1 = (PawnCard) redHand.get(0);
    model.placeCard(1, 0, redCardRow1);


    blueHand = model.getCurrentPlayerHand();
    PawnCard blueCardRow1 = (PawnCard) blueHand.get(0);
    model.placeCard(1, 4, blueCardRow1);


    redHand = model.getCurrentPlayerHand();
    PawnCard redCardRow2 = (PawnCard) redHand.get(0);
    model.placeCard(2, 0, redCardRow2);

    blueHand = model.getCurrentPlayerHand();
    PawnCard blueCardRow2 = (PawnCard) blueHand.get(0);
    model.placeCard(2, 4, blueCardRow2);


    Assert.assertEquals(5, model.calculateTotalScore(PlayerColor.RED));
    Assert.assertEquals(4, model.calculateTotalScore(PlayerColor.BLUE));
  }

  @Test
  public void testDetermineWinnerRed() {
    List<Card> redDeck = new ArrayList<>();
    List<Card> blueDeck = new ArrayList<>();
    HashMap<InfluencePosition, Boolean> emptyInfluence = new HashMap<>();
    PawnCard highRed = new PawnCard("HighRed", 1, 4, emptyInfluence);
    PawnCard lowBlue = new PawnCard("LowBlue", 1, 2, emptyInfluence);

    for (int i = 0; i < 20; i++) {
      redDeck.add(highRed);
      blueDeck.add(lowBlue);
    }

    PawnsGameModel model = new PawnsGameModel(3, 5);
    Assert.assertThrows(IllegalStateException.class, model::determineWinner);
    model.setupGame(redDeck, blueDeck, 1, false);
    //game not over
    Assert.assertThrows(IllegalStateException.class, model::determineWinner);


    for (int r = 0; r < 3; r++) {

      if (model.getCurrentPlayer() != PlayerColor.RED) {
        model.passTurn();
      }

      model.placeCard(r, 0, highRed);


      model.placeCard(r, 4, lowBlue);
    }


    // RED: card with value 4, BLUE: card with value 2.
    // According to scoring rules, for each row, RED's row-score 4 is higher than BLUE's 2
    // so RED gets 4 points per row and BLUE gets 0.
    // Total score for RED should be 3 * 4 = 12, and BLUE should be 0.
    Assert.assertEquals(12, model.calculateTotalScore(PlayerColor.RED));
    Assert.assertEquals(0, model.calculateTotalScore(PlayerColor.BLUE));
    model.passTurn();
    model.passTurn();
    // Determine winner.
    PlayerColor winner = model.determineWinner();
    Assert.assertEquals(PlayerColor.RED, winner);
  }

  @Test
  public void testDetermineWinnerBlue() {
    List<Card> redDeck = new ArrayList<>();
    List<Card> blueDeck = new ArrayList<>();
    HashMap<InfluencePosition, Boolean> emptyInfluence = new HashMap<>();
    PawnCard lowRed = new PawnCard("LowRed", 1, 2, emptyInfluence);
    PawnCard highBlue = new PawnCard("HighBlue", 1, 4, emptyInfluence);
    for (int i = 0; i < 20; i++) {
      redDeck.add(lowRed);
      blueDeck.add(highBlue);
    }

    PawnsGameModel model = new PawnsGameModel(3, 5);
    Assert.assertThrows(IllegalStateException.class, model::determineWinner);
    model.setupGame(redDeck, blueDeck, 1, false);
    //game not over
    Assert.assertThrows(IllegalStateException.class, model::determineWinner);

    for (int r = 0; r < 3; r++) {
      if (model.getCurrentPlayer() != PlayerColor.RED) {
        model.passTurn();
      }

      model.placeCard(r, 0, lowRed);

      model.placeCard(r, 4, highBlue);
    }

    Assert.assertEquals(0, model.calculateTotalScore(PlayerColor.RED));
    Assert.assertEquals(12, model.calculateTotalScore(PlayerColor.BLUE));
    model.passTurn();
    model.passTurn();
    PlayerColor winner = model.determineWinner();
    Assert.assertEquals(PlayerColor.BLUE, winner);
  }

  @Test
  public void testDetermineWinnerTie() {

    List<Card> deck1 = new ArrayList<>();
    List<Card> deck2 = new ArrayList<>();
    HashMap<InfluencePosition, Boolean> emptyInfluence = new HashMap<>();
    PawnCard equalCard = new PawnCard("Equal", 1, 2, emptyInfluence);
    for (int i = 0; i < 20; i++) {
      deck1.add(equalCard);
      deck2.add(equalCard);
    }

    PawnsGameModel model = new PawnsGameModel(3, 5);
    //game not started
    Assert.assertThrows(IllegalStateException.class, model::determineWinner);
    model.setupGame(deck1, deck2, 1, false);

    //game not over
    Assert.assertThrows(IllegalStateException.class, model::determineWinner);

    Assert.assertThrows(IllegalStateException.class, model::determineWinner);
    for (int r = 0; r < 3; r++) {
      if (model.getCurrentPlayer() != PlayerColor.RED) {
        model.passTurn();
      }

      model.placeCard(r, 0, equalCard);
      model.placeCard(r, 4, equalCard);
    }


    Assert.assertEquals(0, model.calculateTotalScore(PlayerColor.RED));
    Assert.assertEquals(0, model.calculateTotalScore(PlayerColor.BLUE));
    model.passTurn();
    model.passTurn();
    PlayerColor winner = model.determineWinner();
    Assert.assertNull(winner);
  }


  @Test
  public void testIsLegalMove() {
    threeByFiveModel.setupGame(new ArrayList<>(sampleDeck), new ArrayList<>(sampleDeck),
            5, false);
    // Current turn is RED
    // Outofbound indices
    Assert.assertFalse(threeByFiveModel.isLegalMove(-1, 0, sampleDeck.get(0)));
    Assert.assertFalse(threeByFiveModel.isLegalMove(0, -1, sampleDeck.get(0)));
    Assert.assertFalse(threeByFiveModel.isLegalMove(3, 0, sampleDeck.get(0)));
    Assert.assertFalse(threeByFiveModel.isLegalMove(0, 5, sampleDeck.get(0)));
    // Middle cell has 0 pawns.
    Assert.assertFalse(threeByFiveModel.isLegalMove(1, 2, sampleDeck.get(0)));
    // For RED, first column  has 1 pawn.
    PawnCard cost1Card = new PawnCard("Cost1", 1, 2,
            new HashMap<InfluencePosition, Boolean>());
    Assert.assertTrue(threeByFiveModel.isLegalMove(1, 0, cost1Card));
    // For RED, a card with cost 2 on a cell with 1 pawn.
    PawnCard cost2Card = new PawnCard("Cost2", 2, 3,
            new HashMap<InfluencePosition, Boolean>());
    Assert.assertFalse(threeByFiveModel.isLegalMove(1, 0, cost2Card));
    //cell already occupied
    threeByFiveModel.placeCard(1, 0, cost1Card);
    Assert.assertFalse(threeByFiveModel.isLegalMove(1, 0, cost1Card));

    // Test not player's turn: cell in last column  belongs to BLUE.
    threeByFiveModel.passTurn();
    Assert.assertFalse(threeByFiveModel.isLegalMove(1, 4, cost1Card));
  }


}