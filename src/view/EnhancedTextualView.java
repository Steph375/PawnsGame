package view;


import model.BoardCell;
import model.PawnsGame;
import model.PlayerColor;

/**
 * Textual view for the PawnsBoard game that supports displaying influence modifiers.
 */
public class EnhancedTextualView extends PawnsTextualView {

  /**
   * Constructor to run textual view for the enhanced version.
   * @param model takes an enhanced version of PawnsGame
   */
  public EnhancedTextualView(PawnsGame model) {
    super(model);
  }

  @Override
  protected String getCellRepresentation(BoardCell cell) {
    if (cell.getCard() != null) {
      int modified = 0;

      // Add upgrade/devalue values if the board cell has those
      if (cell.getUpgrade() > 0) {
        modified += cell.getUpgrade();
      }
      if (cell.getDevalue() > 0) {
        modified -= cell.getDevalue();
      }

      if (cell.getCard().getValueScore() - cell.getDevalue() + cell.getUpgrade() < 0) {
        return "_ "; // Card should have been removed by model logic
      }

      if (cell.getColor() == PlayerColor.RED) {
        return "R+" + modified + " ";
      } else {
        return "B+" + modified + " ";
      }
    } else {
      return cellRepresentationHelper(cell);
      }
    }

  private String cellRepresentationHelper(BoardCell cell) {
    int pawns = cell.getPawns();
    int adjustment = cell.getUpgrade() - cell.getDevalue();

    if (pawns > 0 && adjustment > 0) {
      return pawns + "+" + adjustment + " ";
    }
    else if (pawns > 0 && adjustment < 0) {
      return pawns + adjustment + " ";
    }
    else if(pawns > 0 && adjustment == 0) {
      return pawns + " ";
    }
    else if (pawns == 0 && adjustment > 0) {
      return "+" + adjustment + " ";
    }
    else if (pawns == 0 && adjustment < 0) {
      return adjustment + " ";
    }
    else {
      return "_ ";
    }
  }
}


