package model;

import java.util.Arrays;
import java.util.Objects;

/**
 * A class representing a card in a game of Pawns Board.
 */
public class Card {

  public Integer cost;
  public Integer value;
  public Integer[][] influence;
  public String name;
  PlayerTeam ownerTeam;

  /**
   * Constructor, copies all fields.
   *
   * @param card the card to copy from.
   */
  public Card(Card card) {
    this.cost = card.cost;
    this.value = card.value;
    this.influence = card.influence;
    this.name = card.name;
    this.ownerTeam = card.getOwnerTeam();
  }

  /**
   * Constructor for card class.
   *
   * @param influence the influence of a card, in an 2d int array 5x5. 1 means it has influence, 0
   *                  means none.
   * @param cost      the cost of the card.
   * @param value     the value of the card.
   */
  public Card(Integer[][] influence, Integer cost, Integer value, String name) {
    if (influence == null || influence.length != 5 || influence[0].length != 5) {
      throw new IllegalArgumentException();
    }
    this.influence = influence;
    this.cost = cost;
    this.value = value;
    this.name = name;
  }

  /**
   * getter for the owner of this card.
   *
   * @return the owner.
   */
  public PlayerTeam getOwnerTeam() {
    return ownerTeam;
  }

  /**
   * setter for the owner of this card.
   *
   * @param ownerTeam the owner.
   */
  public void setOwnerTeam(PlayerTeam ownerTeam) {
    this.ownerTeam = ownerTeam;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Card) {
      return Objects.equals(((Card) o).cost, this.cost)
          && Objects.equals(((Card) o).value, this.value)
          && Objects.equals(this.name, ((Card) o).name)
          && Arrays.deepEquals(this.influence, ((Card) o).influence);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(cost, value, name, Arrays.deepHashCode(influence));
  }
}
