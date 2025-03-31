package model;

import java.util.Objects;

/**
 * An influence position is a position representation for the cards influence section.
 * (0,0) represents the card, in the center of the influence grid. The cell directly above the card
 * would be represented by the position (0, 1), while the one directly below would be (0, -1).
 * The cell directly to the left of the card would have the position (-1, 0).
 * The cell directly to the right of the card would have the position (1, 0).
 */
public final class InfluencePosition {
  private final int x;
  private final int y;

  /**
   * Constructs an influence position which is the same constructor as any position.
   *
   * @param x int represent x position
   * @param y int represent y position
   */
  public InfluencePosition(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x value of the position.
   *
   * @return int representing x
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y value of the position.
   *
   * @return int representing y
   */
  public int getY() {
    return y;
  }

  /**
   * Checks to see if the two positions are equal given their fields.
   *
   * @param other object being compared
   * @return boolean stating whether equal or not
   */
  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof InfluencePosition)) {
      return false;
    }
    InfluencePosition that = (InfluencePosition) other;
    return this.x == that.x && this.y == that.y;
  }

  /**
   * Checks to see if the two positions are equal given their fields.
   *
   * @return int hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}