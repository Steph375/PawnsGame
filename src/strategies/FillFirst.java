package strategies;




import model.Card;
import model.PawnsGameReadOnly;
import model.PlayerColor;

/**
 * Choose the first card and location that can be played on and play there.
 */
public class FillFirst implements Strategies {
  /**
   * Empty constructor to call class.
   */
  public FillFirst() {
    //empty
  }

  @Override
  public Move chooseMove(PawnsGameReadOnly model) {
    PlayerColor current = model.getCurrentPlayer();

    for (Card card : model.getCurrentPlayerHand()) {
      for (int row = 0; row < model.getHeight(); row++) {

        // Adjust column direction based on player
        if (current == PlayerColor.RED) {
          for (int col = 0; col < model.getWidth(); col++) {
            if (model.isLegalMove(row, col, card)) {
              return new Move(card, row, col);
            }
          }
        } else { // BLUE
          for (int col = model.getWidth() - 1; col >= 0; col--) {
            if (model.isLegalMove(row, col, card)) {
              return new Move(card, row, col);
            }
          }
        }
      }
    }
    return null;
  }
}


