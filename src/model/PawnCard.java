package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


/**
 * Represents a PawnGame Card that has a name cost value-score and influence. The card's influence
 * is represented as a hashmap of InfluencePosition to Boolean, tracking the surrounding cells, and
 * whether or not the card has influence over them.
 * The cards position is (0,0) with the card directly above being (0, 1) directly below (0,-1),
 * directly right (1,0) and left (-1, 0) etc.
 */
public class PawnCard implements Card {
  private final String name;
  private final int cost;
  private final int valueScore;
  private final HashMap<InfluencePosition, Boolean> influence;

  /**
   * Standard Constructor for Pawn with cost valuescore influence and name.
   *
   * @param name       of card
   * @param cost       of card
   * @param valueScore value of card
   * @param influence  the influence the card has over other cell with no card on the board.
   */
  public PawnCard(String name, int cost, int valueScore, HashMap<InfluencePosition,
          Boolean> influence) {
    if (cost < 1 || cost > 3) {
      throw new IllegalArgumentException("cost must be between 1 and 3");
    }
    if (valueScore < 1) {
      throw new IllegalArgumentException("valueScore must be greater than 0");
    }
    this.name = name;
    this.cost = cost;
    this.valueScore = valueScore;
    this.influence = influence;
  }

  /**
   * Gets the name of the card.
   *
   * @return String of the name
   */
  public String getName() {
    return name;
  }

  /**
   * Get the value score of this card.
   *
   * @return the positive integer used to determine the overall score of the game.
   */
  @Override
  public int getValueScore() {
    return this.valueScore;
  }

  /**
   * Get the cost of the card.
   *
   * @return the cost to place this card.
   */
  @Override
  public int getCost() {
    return this.cost;
  }

  /**
   * Get the influence of this card on the board.
   *
   * @return return a list of positions relative to the cards position
   *         that the card has influence on.
   */
  @Override
  public List<InfluencePosition> getInfluence() {
    List<InfluencePosition> influencedPositions = new ArrayList<>();
    // for each position in the hashmap
    for (InfluencePosition pos : this.influence.keySet()) {
      // gets the boolean from the position if its true adds that position to list
      if (this.influence.get(pos)) {
        influencedPositions.add(pos);
      }
    }
    return influencedPositions;
  }

  /**
   * Mirrors the influence board so it works for blue cards.
   *
   * @return a new card with the same info but a reversed x position for influences.
   */
  @Override
  public Card mirrorInfluence() {
    HashMap<InfluencePosition, Boolean> influence = new HashMap<>();
    for (InfluencePosition pos : this.influence.keySet()) {
      int posX = pos.getX() * -1;
      InfluencePosition in = new InfluencePosition(posX, pos.getY());
      influence.put(in, this.influence.get(pos));

    }

    return new PawnCard(this.name, this.cost, this.valueScore, influence);

  }

  /**
   * Ensures that a separate object with all the same matching fields is considered te same.
   *
   * @param other object being compared
   * @return boolean of whether they are the same
   */
  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof PawnCard)) {
      return false;
    }
    PawnCard that = (PawnCard) other;
    return this.cost == that.cost && this.valueScore ==
            that.valueScore && this.influence.equals(that.influence);
  }

  /**
   * Ensures that a separate object with all the same matching fields is considered te same.
   *
   * @return int hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.cost, this.valueScore, this.influence);
  }

}
