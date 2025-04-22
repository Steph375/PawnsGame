package model;

/**
 * Enhanced cell has the same functionality as the Cell class but also keeps
 * track of the upgrade and devalue points.
 */
public class EnhancedCell extends Cell {
  // the number of upgrade effects applied to this cell
  private int upgrade;
  // the number of devalue affects applied to this cell
  private int devalue;

  /**
   * Constructor takes the same things as a cell but intializes upgrade and devalue to 0.
   * @param pawns number of pawns a cell has.
   * @param card the card if there is one in a cell can be null
   * @param color the color of the owner of the cell
   */
  public EnhancedCell(int pawns, Card card, PlayerColor color) {
    super(pawns, card, color);
    this.upgrade = 0;
    this.devalue = 0;
  }

  public void applyUpgrade() {
    this.upgrade++;
  }

  public void applyDevalue() {
    this.devalue++; // increase the number of devalues by one
  }

  public int getUpgrade() {
    return this.upgrade;
  }

  public int getDevalue() {
    return this.devalue;
  }

  public void resetModifiers() {
    this.upgrade = 0;
    this.devalue = 0;
  }

}
