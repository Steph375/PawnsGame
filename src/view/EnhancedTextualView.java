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
   *
   * @param model takes an enhanced version of PawnsGame
   */
  public EnhancedTextualView(PawnsGame model) {
    super(model);
  }

  @Override
  protected String getCellRepresentation(BoardCell cell) {
    int pawns = cell.getPawns();
    int upgrade = cell.getUpgrade();
    int devalue = cell.getDevalue();
    int modifier = upgrade - devalue;

    if (cell.getCard() != null) {
      int base = cell.getCard().getValueScore();
      int adjusted = base + modifier;

      // If card was removed due to 0 score, show "_"
      if (adjusted <= 0) {
        return "_ ";
      }

      String colorPrefix = cell.getColor() == PlayerColor.RED ? "R" : "B";
      String mod = modifier >= 0 ? "+" + modifier : String.valueOf(modifier);
      return colorPrefix + mod + " ";
    }

    // If pawns exist, show them and modifier if any
    if (pawns > 0) {
      if (modifier != 0) {
        return pawns + (modifier > 0 ? "+" + modifier : String.valueOf(modifier)) + " ";
      } else {
        return pawns + " ";
      }
    }


    if (modifier > 0) {
      return "+" + modifier + " ";
    } else if (modifier < 0) {
      return modifier + " ";
    }

    return "_ ";
  }
}


