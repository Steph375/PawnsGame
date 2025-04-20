package view;


import model.BoardCell;
import model.PawnsGame;
import model.PlayerColor;

/**
 * Textual view for the PawnsBoard game that supports displaying influence modifiers.
 */
public class EnhancedTextualView implements TextualView {
  private final PawnsGame model;

  public EnhancedTextualView(PawnsGame model) {
    this.model = model;
  }

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

  private String getCellRepresentation(BoardCell cell) {
    if (cell.getCard() != null) {
      int modified = cell.getCard().getValueScore();

      // Add upgrade/devalue values if the board cell has those
      if (cell.getUpgrade() > 0) {
        modified += cell.getUpgrade();
      }
      if (cell.getDevalue() > 0) {
        modified -= cell.getDevalue();
      }

      if (modified <= 0) {
        return "0"; // Card should have been removed by model logic
      }

      if (cell.getColor() == PlayerColor.RED) {
        return "R+" + modified;
      } else {
        return "B+" + modified;
      }
    } else {
      int pawns = cell.getPawns();
      if (pawns > 0) {
        String mod = "";
        if (cell.getUpgrade() > 0) {
          mod += "+" + cell.getUpgrade();
        } else if (cell.getDevalue() > 0) {
          mod += "-" + cell.getDevalue();
        }
        return pawns + mod;
      } else {
        return "_";
      }
    }
  }
}


