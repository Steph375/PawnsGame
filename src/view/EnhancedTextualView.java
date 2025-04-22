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
      int pawns = cell.getPawns();
      if (pawns > 0) {
        String mod = "";
        if (cell.getUpgrade() > 0) {
          mod += "+" + cell.getUpgrade();
        } else if (cell.getDevalue() > 0) {
          mod += "-" + cell.getDevalue();
        }
        return pawns +  mod + " ";
      } else {
        return "_ ";
      }
    }
  }
}


