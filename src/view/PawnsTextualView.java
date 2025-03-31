package view;

import model.BoardCell;
import model.PawnsGame;
import model.PlayerColor;

/**
 * Represents the view of the PawnsBoard Game.
 */
public class PawnsTextualView implements TextualView {
  private final PawnsGame model;

  /**
   * Constructs a textual view for the given game model.
   *
   * @param model the game model to render.
   */
  public PawnsTextualView(PawnsGame model) {
    this.model = model;
  }

  /**
   * Returns a string representation of the current board state.
   * Each row is formatted with the red row-score on the left, the row's cells in the middle,
   * and the blue row-score on the right.
   *
   * @return the board state as a string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    BoardCell[][] board = model.getBoard();
    for (int i = 0; i < board.length; i++) {
      if (i == board.length - 1) {
        sb.append(renderRow(board[i]));
      } else {
        sb.append(renderRow(board[i])).append("\n");
      }
    }

    return sb.toString();
  }

  /**
   * Renders a single row of board cells.
   *
   * @param row an array of BoardCell objects for a single row.
   * @return a string representing that row with scores.
   */
  private String renderRow(BoardCell[] row) {
    int redRowScore = 0;
    int blueRowScore = 0;
    StringBuilder rowRepresentation = new StringBuilder();

    for (BoardCell cell : row) {
      rowRepresentation.append(getCellRepresentation(cell));

      if (cell.getCard() != null) {
        if (cell.getColor() == PlayerColor.RED) {
          redRowScore += cell.getCard().getValueScore();
        } else if (cell.getColor() == PlayerColor.BLUE) {
          blueRowScore += cell.getCard().getValueScore();
        }
      }
    }

    return redRowScore + " " + rowRepresentation + " " + blueRowScore;
  }

  /**
   * Returns a string representation for a single cell.
   *
   * @param cell the BoardCell to represent.
   * @return "R" if the cell is red, "B" if blue,
   *         the number of pawns if present, or "_" if empty.
   */
  private String getCellRepresentation(BoardCell cell) {
    if (cell.getCard() != null) {
      if (cell.getColor() == PlayerColor.RED) {
        return "R";
      } else {
        return "B";
      }
    } else {
      int pawns = cell.getPawns();
      if (pawns > 0) {
        return String.valueOf(pawns);
      } else {
        return "_";
      }
    }
  }
}

