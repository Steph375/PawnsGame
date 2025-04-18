package model;

import java.util.List;

/**
 * A card to use for playing PawnsBoard. Every card has a name, value, and cost, and has influence
 * on some cells surrounding it.
 */
public interface Card {
  /**
   * Get the value score of this card.
   *
   * @return the positive integer used to determine the overall score of the game.
   */
  int getValueScore();

  /**
   * Get the cost of the card.
   *
   * @return the cost to place this card.
   */
  int getCost();

  /**
   * Get the influence of this card on the board.
   *
   * @return return a list of positions relative to the cards position that the card has influence.
   */
  List<InfluencePosition> getInfluence();

  /**
   * Mirrors the influence board so it works for blue cards.
   *
   * @return a new card with the same info but a reversed x position for influences.
   */
  Card mirrorInfluence();

  /**
   * Gets the name of the card.
   *
   * @return String of the name
   */
  String getName();

  /**
   * Ensures that a separate object with all the same matching fields is considered te same.
   *
   * @param other object being compared
   * @return boolean of whether they are the same
   */
  boolean equals(Object other);

  /**
   * Ensures that a separate object with all the same matching fields is considered te same.
   *
   * @return int hashcode
   */
  int hashCode();

  List<InfluencePosition> getUpgrades();

  List<InfluencePosition> getDevalues();
}
