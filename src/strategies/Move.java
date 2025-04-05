package strategies;

import java.util.Objects;
import model.Card;

/**
 * Represents a move in the game PawnsBoard.
 */
public class Move {
  private final Card card;
  private  final int row;
  private final int col;

  /**
   * Creates a move that can be made in the game PawnsBoard.
   * @param card the card to be placed.
   * @param row the row to place the card.
   * @param col the column to place the card.
   */
  public Move(Card card, int row, int col) {
    this.card = card;
    this.row = row;
    this.col = col;
  }

  /**
   * Gets this Move's card.
   * @return the card to be placed.
   */
  public Card getCard() {
    return card;
  }

  /**
   * Gets this Move's row.
   * @return the row to place the card in.
   */
  public int getRow() {
    return row;
  }

  /**
   * Gets this Move's column.
   * @return the column to place the card in.
   */
  public int getCol() {
    return col;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (!(other instanceof Move)) {
      return false;
    }
    Move that = (Move) other;
    return this.card == that.card && this.row == that.row && this.col == that.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.card, this.row, this.col);
  }
}

