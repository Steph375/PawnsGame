
import java.util.ArrayList;
import java.util.List;

import model.BoardCell;
import model.Card;
import model.IPlayer;
import model.PawnsGameReadOnly;
import model.PlayerColor;

/**
 * Mock for the PawnsGame model.
 */
public class MockPawnsGame implements PawnsGameReadOnly {

  public final StringBuilder log = new StringBuilder();
  private final List<Card> hand;
  private final int height;
  private final int width;
  private final boolean[][] legalMoves;
  final int[][] redRowScores;
  final int[][] blueRowScores;
  private final PlayerColor currentPlayer;
  private final IPlayer redPlayer;
  private final IPlayer bluePlayer;

  /**
   * Creates a mock.
   * @param hand the cards in the hand.
   * @param height the height of the board.
   * @param width the width of the board.
   * @param legalMoves 2d array representing whether a move is legal.
   * @param redRowScores the scores for the red player.
   * @param blueRowScores the scores for the blue player.
   * @param currentPlayer the current player in the game.
   */
  public MockPawnsGame(List<Card> hand, int height, int width,
                       boolean[][] legalMoves, int[][] redRowScores,
                       int[][] blueRowScores, PlayerColor currentPlayer) {
    this.hand = hand;
    this.height = height;
    this.width = width;
    this.legalMoves = legalMoves;
    this.redRowScores = redRowScores;
    this.blueRowScores = blueRowScores;
    this.currentPlayer = currentPlayer;

    if (currentPlayer == PlayerColor.RED) {
      this.redPlayer = new DummyPlayer(PlayerColor.RED, hand);
      this.bluePlayer = new DummyPlayer(PlayerColor.BLUE, makeEmptyHand());
    } else {
      this.redPlayer = new DummyPlayer(PlayerColor.RED, makeEmptyHand());
      this.bluePlayer = new DummyPlayer(PlayerColor.BLUE, hand);
    }
  }

  @Override
  public PlayerColor getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public List<Card> getCurrentPlayerHand() {
    return hand;
  }

  @Override
  public boolean isLegalMove(int row, int col, Card card) {
    if (card == null) {
      return false;
    }

    log.append("Checked for legal move with card ").append(card.getName()).append(
            " at row ").append(row).append(" col ").append(col).append(System.lineSeparator());

    if (this.legalMoves[row][col]) {
      log.append("Move was legal").append(System.lineSeparator());
    } else {
      log.append("Move was not legal").append(System.lineSeparator());
    }

    return legalMoves[row][col];
  }

  @Override
  public int[] getRowScores(int row) {
    log.append("getRowScores(").append(row).append(")").append(System.lineSeparator());
    return new int[]{
            redRowScores[row][0],
            blueRowScores[row][0]
    };
  }

  @Override
  public int calculateTotalScore(PlayerColor color) {
    log.append("calculateTotalScore(").append(color).append(")").append(System.lineSeparator());
    int total = 0;
    for (int r = 0; r < redRowScores.length; r++) {
      int red = redRowScores[r][0];
      int blue = blueRowScores[r][0];
      if (color == PlayerColor.RED && red > blue) {
        total += red;
      } else if (color == PlayerColor.BLUE && blue > red) {
        total += blue;
      }
    }
    return total;
  }

  @Override
  public IPlayer getPlayerRed() {
    log.append("getPlayerRed() called").append(System.lineSeparator());
    return redPlayer;
  }

  @Override
  public IPlayer getPlayerBlue() {
    log.append("getPlayerBlue() called").append(System.lineSeparator());
    return bluePlayer;
  }

  private List<Card> makeEmptyHand() {
    List<Card> dummyHand = new ArrayList<>();
    for (int i = 0; i < hand.size(); i++) {
      dummyHand.add(null); // Replace with a dummy Card if needed
    }
    return dummyHand;
  }

  // Stubbed methods (not needed for strategy testing)
  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public BoardCell[][] getBoard() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isGameOver() {
    throw new UnsupportedOperationException();
  }

  @Override
  public PlayerColor determineWinner() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int getPasses() {
    throw new UnsupportedOperationException();
  }
}