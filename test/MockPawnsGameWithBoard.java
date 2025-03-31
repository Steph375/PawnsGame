import java.util.List;

import model.BoardCell;
import model.Card;
import model.PlayerColor;

/**
 * Mock for the model with a board.
 */
public class MockPawnsGameWithBoard extends MockPawnsGame {
  private final BoardCell[][] board;

  /**
   * Creates a mock with a board.
   * @param hand cards in hand.
   * @param height height of the board.
   * @param width width of the board.
   * @param legalMoves legal moves.
   * @param redScores scores for red player.
   * @param blueScores scores for blue player.
   * @param currentPlayer the current player.
   * @param board the board.
   */
  public MockPawnsGameWithBoard(List<Card> hand, int height, int width,
                                boolean[][] legalMoves,
                                int[][] redScores,
                                int[][] blueScores,
                                PlayerColor currentPlayer,
                                BoardCell[][] board) {
    super(hand, height, width, legalMoves, redScores, blueScores, currentPlayer);
    this.board = board;
  }

  @Override
  public BoardCell[][] getBoard() {
    log.append("getBoard()").append(System.lineSeparator());
    return board;
  }
}

