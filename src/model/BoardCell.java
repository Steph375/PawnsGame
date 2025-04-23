package model;

/**
 * Cells of Board.
 */
public interface BoardCell {
  /**
   * Gets the card in cell.
   *
   * @return that card
   */
  Card getCard();

  /**
   * gets the number of pawns in cell.
   *
   * @return num of pawns
   */
  int getPawns();

  /**
   * gets the color of the pawns in cell, if no pawns returns null.
   *
   * @return that color
   */
  PlayerColor getColor();

  /**
   * Adds pawns to the cell.  Will change owner if no owner or different owner
   * and not add pawns if changing owner.
   *
   * @param count the number of pawns to add
   *              caps at 3
   */
  void addPawns(int count, PlayerColor color);

  /**
   * Places a card in the cell. Clears pawns from cell.
   *
   * @param card  the card to place in this cell
   * @param owner the player color who owns this card
   * @throws IllegalArgumentException if card is null
   */
  void placeCard(Card card, PlayerColor owner);


  /**
   * clears all pawns from cell.
   */
  void removeAllPawns();

  /**
   * Adds the upgrade to the cell if the class uses upgrades.
   */
  void applyUpgrade();

  /**
   * Adds the devalue to the cell if the class uses devalues.
   */
  void applyDevalue();

  /**
   * produces te upgrade value field of the cell.
   * @return int of upgrade value
   */
  int getUpgrade();

  /**
   * produces te devalue value field of the cell.
   * @return int of devalue value
   */
  int getDevalue();

  /**
   * Sets all upgrade modifier fields to 0.
   */
  void resetModifiers();

  /**
   * Removes card from  cell by making field null. And pust the cost of the sell
   * as the number of pawns in the cell.
   */
  void clearCard();
}
