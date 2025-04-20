package model;

public class EnhancedCell extends Cell {
  private int upgrade; // the number of upgrade effects applied to this cell
  private int devalue; // the number of devalue affects applied to this cell

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
