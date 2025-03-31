import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import controller.DeckReader;
import model.BoardCell;
import model.Card;
import model.Cell;
import model.PawnsGameModel;
import model.PlayerColor;
import strategies.ControlBoard;
import strategies.FillFirst;
import strategies.MaximizeScore;
import strategies.Move;
import strategies.MultipleStrategies;
import strategies.NoGoodMove;
import strategies.Strategies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * A class to test the behavior of Strategies for the game PawnsBoard.
 */
public class StrategiesTest {

  String path;
  File configFile;
  List<Card> sampleDeck;
  PawnsGameModel starterGame;

  Card security;
  Card bee;
  Card mandragora;
  Card viper;


  @Before
  public void setUp() {
    path = "docs" + File.separator + "deck.config";
    configFile = new File(path);
    sampleDeck = DeckReader.readDeck(configFile);

    starterGame = new PawnsGameModel(3, 5);
    starterGame.startGame(new ArrayList<>(sampleDeck), new ArrayList<>(sampleDeck), 5,
            false);

    security = sampleDeck.get(0);   // Security 1 2
    bee = sampleDeck.get(1);        // Bee 2 3
    mandragora = sampleDeck.get(2); // Mandragora 3 4
    viper = sampleDeck.get(3);      // Viper 1 2
  }

  @Test
  public void testFillFirstPicksFirstLegalMove() {
    List<Card> hand = List.of(security);
    boolean[][] legalMoves = {
            {false, false},
            {true, false}
    };

    MockPawnsGame model = new MockPawnsGame(hand, 2, 2, legalMoves,
            new int[2][1], new int[2][1], PlayerColor.RED);

    Strategies strategy = new FillFirst();
    Move move = strategy.chooseMove(model);

    assertNotNull(move);
    assertEquals(1, move.getRow());
    assertEquals(0, move.getCol());
    assertEquals(security, move.getCard());

    String[] splitLines = model.log.toString().split(System.lineSeparator());

    Assert.assertEquals("Checked for legal move with card Security at row 0 col 0",
            splitLines[0]);
    Assert.assertEquals("Move was not legal", splitLines[1]);
    Assert.assertEquals("Checked for legal move with card Security at row 0 col 1",
            splitLines[2]);
    Assert.assertEquals("Move was not legal", splitLines[3]);
    Assert.assertEquals("Checked for legal move with card Security at row 1 col 0",
            splitLines[4]);
    Assert.assertEquals("Move was legal", splitLines[5]);
  }

  @Test
  public void testFillFirstBlueTopRightTraversal() {
    List<Card> hand = List.of(bee);

    boolean[][] legalMoves = {
            {false, false},
            {true, false}
    };

    MockPawnsGame model = new MockPawnsGame(hand, 2, 2, legalMoves,
            new int[2][1], new int[2][1], PlayerColor.BLUE);

    Strategies strategy = new FillFirst();
    Move move = strategy.chooseMove(model);

    assertNotNull(move);
    assertEquals(1, move.getRow());
    assertEquals(0, move.getCol());
    assertEquals(bee, move.getCard());

    String[] splitLines = model.log.toString().split(System.lineSeparator());

    Assert.assertEquals("Checked for legal move with card Bee at row 0 col 1",
            splitLines[0]);
    Assert.assertEquals("Move was not legal", splitLines[1]);
    Assert.assertEquals("Checked for legal move with card Bee at row 0 col 0",
            splitLines[2]);
    Assert.assertEquals("Move was not legal", splitLines[3]);
    Assert.assertEquals("Checked for legal move with card Bee at row 1 col 1",
            splitLines[4]);
    Assert.assertEquals("Move was not legal", splitLines[5]);
    Assert.assertEquals("Checked for legal move with card Bee at row 1 col 0",
            splitLines[6]);
    Assert.assertEquals("Move was legal", splitLines[7]);
  }

  @Test
  public void testFillFirstTriesNextCardInHand() {
    List<Card> hand = List.of(mandragora, viper);

    boolean[][] legalMoves = {
            {false, false},
            {false, false}
    };

    MockPawnsGame model = new MockPawnsGame(hand, 2, 2, legalMoves,
            new int[2][1], new int[2][1], PlayerColor.RED);

    Strategies strategy = new FillFirst();
    Move move = strategy.chooseMove(model);

    assertNull(move); // no legal moves
    String logOutput = model.log.toString();

    // Confirm both cards were checked
    assertTrue(logOutput.contains("card Mandragora"));
    assertTrue(logOutput.contains("card Viper"));

    System.out.println(logOutput);

    //Checks each time the log checks each card
    int mandragoraChecks = logOutput.split("card Mandragora", -1).length - 1;
    int viperChecks = logOutput.split("card Viper", -1).length - 1;

    assertEquals(4, mandragoraChecks);
    assertEquals(4, viperChecks);
  }

  @Test
  public void testFillFirstReturnsNullWhenNoMoves() {
    List<Card> hand = List.of(security);
    boolean[][] legalMoves = {
            {false, false},
            {false, false}
    };

    MockPawnsGame model = new MockPawnsGame(hand, 2, 2, legalMoves,
            new int[2][1], new int[2][1], PlayerColor.RED);

    Strategies strategy = new FillFirst();
    Move move = strategy.chooseMove(model);
    assertNull(move);

    String[] splitLines = model.log.toString().split(System.lineSeparator());

    Assert.assertEquals("Checked for legal move with card Security at row 0 col 0",
            splitLines[0]);
    Assert.assertEquals("Move was not legal", splitLines[1]);
    Assert.assertEquals("Checked for legal move with card Security at row 0 col 1",
            splitLines[2]);
    Assert.assertEquals("Move was not legal", splitLines[3]);
    Assert.assertEquals("Checked for legal move with card Security at row 1 col 0",
            splitLines[4]);
    Assert.assertEquals("Move was not legal", splitLines[5]);
    Assert.assertEquals("Checked for legal move with card Security at row 1 col 1",
            splitLines[6]);
    Assert.assertEquals("Move was not legal", splitLines[7]);
  }

  @Test
  public void testMaximizeScoreStrategyChoosesCorrectMove() {

    List<Card> hand = List.of(bee); // value = 3

    boolean[][] legalMoves = {
            {true, true},
            {true, true},
            {false, true}
    };

    int[][] redScores = {
            {2}, {1}, {3}
    };
    int[][] blueScores = {
            {3}, {2}, {2}
    };

    MockPawnsGame model = new MockPawnsGame(
            hand, 3, 2, legalMoves,
            redScores, blueScores,
            PlayerColor.RED
    );

    Strategies strategy = new MaximizeScore();
    Move move = strategy.chooseMove(model);

    assertNotNull(move);
    assertEquals(2, move.getRow());
    assertEquals(1, move.getCol());
    assertEquals(bee, move.getCard());

    System.out.println(model.log);

    String[] splitLines = model.log.toString().split(System.lineSeparator());

    Assert.assertEquals("getRowScores(0)", splitLines[0]);
    Assert.assertEquals("Checked for legal move with card Bee at row 0 col 0",
            splitLines[1]);
    Assert.assertEquals("Move was legal", splitLines[2]);
    Assert.assertEquals("Checked for legal move with card Bee at row 0 col 1",
            splitLines[3]);
    Assert.assertEquals("Move was legal", splitLines[4]);
    Assert.assertEquals("getRowScores(1)", splitLines[5]);
    Assert.assertEquals("Checked for legal move with card Bee at row 1 col 0",
            splitLines[6]);
    Assert.assertEquals("Move was legal", splitLines[7]);
    Assert.assertEquals("Checked for legal move with card Bee at row 1 col 1",
            splitLines[8]);
    Assert.assertEquals("Move was legal", splitLines[9]);
    Assert.assertEquals("getRowScores(2)", splitLines[10]);
    Assert.assertEquals("Checked for legal move with card Bee at row 2 col 0",
            splitLines[11]);
    Assert.assertEquals("Move was not legal", splitLines[12]);
    Assert.assertEquals("Checked for legal move with card Bee at row 2 col 1",
            splitLines[13]);
    Assert.assertEquals("Move was legal", splitLines[14]);
  }

  @Test
  public void testMaximizeScoreReturnsNullWhenFull() {
    List<Card> hand = List.of(security); // value = 2

    boolean[][] legalMoves = {
            {false, false},
            {false, false}
    };

    int[][] redScores = {
            {5}, {6}
    };
    int[][] blueScores = {
            {3}, {2}
    };

    // Build a fully filled board
    BoardCell[][] fullBoard = new BoardCell[2][2];
    for (int r = 0; r < 2; r++) {
      for (int c = 0; c < 2; c++) {
        fullBoard[r][c] = new Cell(0, security, PlayerColor.RED); // every cell has a card
      }
    }

    MockPawnsGameWithBoard model = new MockPawnsGameWithBoard(
            hand, 2, 2, legalMoves,
            redScores, blueScores,
            PlayerColor.RED,
            fullBoard
    );

    Strategies strategy = new MaximizeScore();
    Move move = strategy.chooseMove(model);


    String[] splitLines = model.log.toString().split(System.lineSeparator());

    Assert.assertEquals("getRowScores(0)", splitLines[0]);
    Assert.assertEquals("Checked for legal move with card Security at row 0 col 0",
            splitLines[1]);
    Assert.assertEquals("Move was not legal", splitLines[2]);
    Assert.assertEquals("Checked for legal move with card Security at row 0 col 1",
            splitLines[3]);
    Assert.assertEquals("Move was not legal", splitLines[4]);
    Assert.assertEquals("getRowScores(1)", splitLines[5]);
    Assert.assertEquals("Checked for legal move with card Security at row 1 col 0",
            splitLines[6]);
    Assert.assertEquals("Move was not legal", splitLines[7]);
    Assert.assertEquals("Checked for legal move with card Security at row 1 col 1",
            splitLines[8]);
    Assert.assertEquals("Move was not legal", splitLines[9]);
  }


  @Test
  public void testControlBoardStrategyChoosesCorrectCell() {
    List<Card> hand = List.of(security);

    boolean[][] legalMoves = {
            {true, true},
            {true, true}
    };

    BoardCell[][] board = new BoardCell[2][2];
    board[0][0] = new Cell(1, null, PlayerColor.RED);
    board[0][1] = new Cell(1, null, PlayerColor.BLUE);
    board[1][0] = new Cell(1, null, PlayerColor.RED);
    board[1][1] = new Cell(0, null, null);

    MockPawnsGameWithBoard model = new MockPawnsGameWithBoard(
            hand, 2, 2, legalMoves,
            new int[2][1], new int[2][1],
            PlayerColor.RED, board
    );

    Strategies strategy = new ControlBoard();
    Move move = strategy.chooseMove(model);

    assertNotNull(move);
    assertEquals(0, move.getRow());
    assertEquals(0, move.getCol());
    assertEquals(security, move.getCard());

    assertTrue(model.log.toString().contains("getBoard()"));


    String[] splitLines = model.log.toString().split(System.lineSeparator());

    Assert.assertEquals("Checked for legal move with card Security at row 0 col 0",
            splitLines[0]);
    Assert.assertEquals("Move was legal", splitLines[1]);
    Assert.assertEquals("getBoard()", splitLines[2]);
    Assert.assertEquals("Checked for legal move with card Security at row 0 col 1",
            splitLines[3]);
    Assert.assertEquals("Move was legal", splitLines[4]);
    Assert.assertEquals("getBoard()", splitLines[5]);
    Assert.assertEquals("Checked for legal move with card Security at row 1 col 0",
            splitLines[6]);
    Assert.assertEquals("Move was legal", splitLines[7]);
    Assert.assertEquals("getBoard()", splitLines[8]);
    Assert.assertEquals("Checked for legal move with card Security at row 1 col 1",
            splitLines[9]);
    Assert.assertEquals("Move was legal", splitLines[10]);
    Assert.assertEquals("getBoard()", splitLines[11]);
  }

  @Test
  public void testControlBoardChoosesTopLeftInTie() {
    List<Card> hand = List.of(bee); // doesn't affect tie-breaking logic

    boolean[][] legalMoves = {
            {true, true},
            {true, true}
    };

    // All cells are equal — same pawn count, same color
    BoardCell[][] board = new BoardCell[2][2];
    for (int r = 0; r < 2; r++) {
      for (int c = 0; c < 2; c++) {
        board[r][c] = new Cell(1, null, PlayerColor.RED);
      }
    }

    MockPawnsGameWithBoard model = new MockPawnsGameWithBoard(
            hand, 2, 2, legalMoves,
            new int[2][1], new int[2][1],
            PlayerColor.RED, board
    );

    Strategies strategy = new ControlBoard();
    Move move = strategy.chooseMove(model);

    assertNotNull(move);
    assertEquals(0, move.getRow());
    assertEquals(0, move.getCol()); // Tie-breaker: top-left


    String[] splitLines = model.log.toString().split(System.lineSeparator());

    Assert.assertEquals("Checked for legal move with card Bee at row 0 col 0",
            splitLines[0]);
    Assert.assertEquals("Move was legal", splitLines[1]);
    Assert.assertEquals("getBoard()", splitLines[2]);
    Assert.assertEquals("Checked for legal move with card Bee at row 0 col 1",
            splitLines[3]);
    Assert.assertEquals("Move was legal", splitLines[4]);
    Assert.assertEquals("getBoard()", splitLines[5]);
    Assert.assertEquals("Checked for legal move with card Bee at row 1 col 0",
            splitLines[6]);
    Assert.assertEquals("Move was legal", splitLines[7]);
    Assert.assertEquals("getBoard()", splitLines[8]);
    Assert.assertEquals("Checked for legal move with card Bee at row 1 col 1",
            splitLines[9]);
    Assert.assertEquals("Move was legal", splitLines[10]);
    Assert.assertEquals("getBoard()", splitLines[11]);

  }

  @Test
  public void testControlBoardReturnsNullWhenNoLegalMoves() {
    List<Card> hand = List.of(bee);

    boolean[][] legalMoves = {
            {false, false},
            {false, false}
    };

    // Full board
    BoardCell[][] board = new BoardCell[2][2];
    board[0][0] = new Cell(0, bee, PlayerColor.RED);
    board[0][1] = new Cell(0, mandragora, PlayerColor.BLUE);
    board[1][0] = new Cell(0, viper, PlayerColor.RED);
    board[1][1] = new Cell(0, security, PlayerColor.BLUE);

    MockPawnsGameWithBoard model = new MockPawnsGameWithBoard(
            hand, 2, 2, legalMoves,
            new int[2][1], new int[2][1],
            PlayerColor.RED, board
    );

    Strategies strategy = new ControlBoard();
    Move move = strategy.chooseMove(model);

    assertNull(move);

    String[] splitLines = model.log.toString().split(System.lineSeparator());
    Assert.assertEquals("Checked for legal move with card Bee at row 0 col 0",
            splitLines[0]);
    Assert.assertEquals("Move was not legal", splitLines[1]);
    Assert.assertEquals("Checked for legal move with card Bee at row 0 col 1",
            splitLines[2]);
    Assert.assertEquals("Move was not legal", splitLines[3]);
    Assert.assertEquals("Checked for legal move with card Bee at row 1 col 0",
            splitLines[4]);
    Assert.assertEquals("Move was not legal", splitLines[5]);
    Assert.assertEquals("Checked for legal move with card Bee at row 1 col 1",
            splitLines[6]);
    Assert.assertEquals("Move was not legal", splitLines[7]);
  }


  @Test
  public void testNoGoodMovePicksLegalMoveIfAvailable() {
    List<Card> hand = List.of(mandragora);

    boolean[][] legalMoves = {
            {false, false},
            {true, false}
    };

    MockPawnsGame model = new MockPawnsGame(hand, 2, 2, legalMoves,
            new int[2][1], new int[2][1],
            PlayerColor.RED);

    Strategies strategy = new NoGoodMove();

    Move move = strategy.chooseMove(model);

    assertNotNull(move);
    assertEquals(1, move.getRow());
    assertEquals(0, move.getCol());
    assertEquals(mandragora, move.getCard());

    System.out.println(model.log);
    String[] splitLines = model.log.toString().split(System.lineSeparator());
    Assert.assertEquals("Checked for legal move with card Mandragora at row 0 col 0",
            splitLines[0]);
    Assert.assertEquals("Move was not legal", splitLines[1]);
    Assert.assertEquals("Checked for legal move with card Mandragora at row 0 col 1",
            splitLines[2]);
    Assert.assertEquals("Move was not legal", splitLines[3]);
    Assert.assertEquals("Checked for legal move with card Mandragora at row 1 col 0",
            splitLines[4]);
    Assert.assertEquals("Move was legal", splitLines[5]);
    Assert.assertEquals("getPlayerBlue() called",
            splitLines[6]);
    Assert.assertEquals("Checked for legal move with card Mandragora at row 1 col 1",
            splitLines[7]);
    Assert.assertEquals("Move was not legal", splitLines[8]);

  }


  @Test
  public void testNoGoodMoveStrategyReturnsNull() {
    List<Card> hand = List.of(security);
    boolean[][] legalMoves = {
            {false, false},
            {false, false}
    };

    MockPawnsGame model = new MockPawnsGame(
            hand, 2, 2, legalMoves,
            new int[2][1], new int[2][1],
            PlayerColor.RED
    );

    Strategies strategy = new NoGoodMove();
    Move move = strategy.chooseMove(model);

    assertNull(move);

    System.out.println(model.log);
    String[] splitLines = model.log.toString().split(System.lineSeparator());
    Assert.assertEquals("Checked for legal move with card Security at row 0 col 0",
            splitLines[0]);
    Assert.assertEquals("Move was not legal", splitLines[1]);
    Assert.assertEquals("Checked for legal move with card Security at row 0 col 1",
            splitLines[2]);
    Assert.assertEquals("Move was not legal", splitLines[3]);
    Assert.assertEquals("Checked for legal move with card Security at row 1 col 0",
            splitLines[4]);
    Assert.assertEquals("Move was not legal", splitLines[5]);
    Assert.assertEquals("Checked for legal move with card Security at row 1 col 1",
            splitLines[6]);
    Assert.assertEquals("Move was not legal", splitLines[7]);
  }

  @Test
  public void testMultipleStrategiesFallsBackCorrectly() {
    List<Card> hand = List.of(viper);

    boolean[][] legalMoves = {{false, false}, {false, true}};

    int[][] redScores = {{1}, {1}};
    int[][] blueScores = {
            {5}, {2}
    };

    MockPawnsGame model = new MockPawnsGame(hand, 2, 2, legalMoves,
            redScores, blueScores, PlayerColor.RED);

    List<Strategies> chain = List.of(
            new MaximizeScore(),
            new ControlBoard(),
            new FillFirst()
    );

    Strategies fallback = new MultipleStrategies(chain);
    Move move = fallback.chooseMove(model);

    assertNotNull(move);
    assertEquals(1, move.getRow());
    assertEquals(1, move.getCol());
    assertEquals(viper, move.getCard());
    System.out.println(model.log);

    System.out.println(model.log);
    String[] splitLines = model.log.toString().split(System.lineSeparator());
    Assert.assertEquals("getRowScores(0)",
            splitLines[0]);
    Assert.assertEquals("Checked for legal move with card Viper at row 0 col 0",
            splitLines[1]);
    Assert.assertEquals("Move was not legal",
            splitLines[2]);
    Assert.assertEquals("Checked for legal move with card Viper at row 0 col 1",
            splitLines[3]);
    Assert.assertEquals("Move was not legal",
            splitLines[4]);
    Assert.assertEquals("getRowScores(1)", splitLines[5]);
    Assert.assertEquals("Checked for legal move with card Viper at row 1 col 0",
            splitLines[6]);
    Assert.assertEquals("Move was not legal", splitLines[7]);
    Assert.assertEquals("Checked for legal move with card Viper at row 1 col 1",
            splitLines[8]);
    Assert.assertEquals("Move was legal",
            splitLines[9]);


  }
}
