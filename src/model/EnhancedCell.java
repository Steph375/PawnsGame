package model;

public class EnhancedCell extends Cell {
  private int upgrade;
  private int devalue;

  public EnhancedCell(int pawns, Card card, PlayerColor color) {
    super(pawns, card, color);
    this.upgrade = 0;
    this.devalue = 0;
  }

  public void applyUpgrade() {
    this.upgrade++;
  }

  public void applyDevalue() {
    this.devalue++;
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
