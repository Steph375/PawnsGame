package model;

/**
 * Class represents a board cell.
 */
public class Cell implements BoardCell {
  private int pawns;
  protected Card card;
  private PlayerColor color;

  /**
   * Basic Constructor for Cell.
   *
   * @param pawns number of pawns in the cell to start.
   * @param card  the card in the cell to start.
   * @param color owner of cell.
   */
  public Cell(int pawns, Card card, PlayerColor color) {
    if (pawns > 0 && color == null) {
      throw new IllegalArgumentException("non empty cell needs owner");
    }
    if (pawns < 0 || pawns > 3) {
      throw new IllegalArgumentException("pawns must be between 0 and 3");
    }
    this.pawns = pawns;
    this.card = card;
    this.color = color;
  }


  /**
   * Gets the card in cell.
   *
   * @return that card
   */
  @Override
  public Card getCard() {
    return this.card;
  }

  /**
   * gets the number of pawns in cell.
   *
   * @return num of pawns
   */
  @Override
  public int getPawns() {
    return this.pawns;
  }

  /**
   * gets the color of the pawns in cell, if no pawns returns null.
   *
   * @return that color
   */
  @Override
  public PlayerColor getColor() {
    return this.color;
  }

  /**
   * Adds pawns to the cell. Will change owner if no owner or different owner
   * and not add pawns if changing owner.
   *
   * @param count the number of pawns to add
   *              caps at 3
   */
  @Override
  public void addPawns(int count, PlayerColor owner) {
    // If a card is present, don't change anything.
    if (this.card != null) {
      return;
    }

    if (this.pawns == 0) {
      // Empty cell: add pawns and set the owner.
      this.pawns = count;
      this.color = owner;
    } else if (this.color == owner) {
      // Same owner add the pawns.
      this.pawns += count;
    } else {
      // change the ownership (but don't add pawns).
      this.color = owner;
    }

    if (this.pawns > 3) {
      this.pawns = 3;
    }
  }


  /**
   * Places a card in the cell. Clears pawns from cell.
   *
   * @param card  the card to place in this cell
   * @param owner the player color who owns this card
   * @throws IllegalArgumentException if card is null
   */
  @Override
  public void placeCard(Card card, PlayerColor owner) {
    if (card == null) {
      throw new IllegalArgumentException("Cannot place a null card.");
    }

    this.card = card;
    this.color = owner;
  }

  /**
   * clears all pawns from cell.
   */
  @Override
  public void removeAllPawns() {
    this.pawns = 0;
  }

  @Override
  public void applyUpgrade() {

  }

  @Override
  public void applyDevalue() {

  }

  @Override
  public int getUpgrade() {
    return 0;
  }

  @Override
  public int getDevalue() {
    return 0;
  }

  @Override
  public void resetModifiers() {

  }

  @Override
  public void clearCard() {
    this.card = null;
  }
}
