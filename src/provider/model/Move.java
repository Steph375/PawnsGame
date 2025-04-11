package provider.model;

/**
 * This class reprisents a move a player can make. They can either pass or play a card, and the
 * particulars about which card to place and where are stored in this class. the number parameters
 * are unnecisary if the move is a pass.
 */
public class Move {

  public MoveType moveType;
  public Integer placeRow;
  public Integer placeCol;
  public Integer handIdx;

  /**
   * Constructor for the move class.
   *
   * @param moveType which type of move to make.
   * @param placeRow where to place the card (if that applies).
   * @param placeCol where to place the card (if that applies).
   * @param handIdx  which card to place (if that applies).
   */
  public Move(MoveType moveType, Integer placeRow, Integer placeCol, Integer handIdx) {
    this.moveType = moveType;
    this.placeRow = placeRow;
    this.placeCol = placeCol;
    this.handIdx = handIdx;
  }

  /**
   * Constructor for the move class.
   *
   * @param moveType which type of move.
   */
  public Move(MoveType moveType) {
    this.moveType = moveType; // used if the move is a pass
  }
}
