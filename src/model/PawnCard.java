package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


import java.util.Map;

/**
 * Represents a PawnGame Card that has a name, cost, value-score and influence. The card's influence
 * is represented as a hashmap of InfluencePosition to InfluenceType, tracking the surrounding cells
 * and the type of influence applied. Default implementation only uses INFLUENCE type.
 */
public class PawnCard implements Card {
  private final String name;
  private final int cost;
  private final int valueScore;
  protected final Map<InfluencePosition, InfluenceType> influence;

  /**
   * Constructor to create pawn card for Pawns game.
   * @param name of the card
   * @param cost cost to place card on game board.
   * @param valueScore score given wen card placed.
   * @param influence influence card has on the board.
   */
  public PawnCard(String name, int cost, int valueScore,
                  Map<InfluencePosition, InfluenceType> influence) {
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

  public String getName() {
    return name;
  }

  @Override
  public int getValueScore() {
    return this.valueScore;
  }

  @Override
  public int getCost() {
    return this.cost;
  }

  @Override
  public List<InfluencePosition> getInfluence() {
    List<InfluencePosition> influencedPositions = new ArrayList<>();
    for (Map.Entry<InfluencePosition, InfluenceType> entry : this.influence.entrySet()) {
      if (entry.getValue() == InfluenceType.INFLUENCE) {
        influencedPositions.add(entry.getKey());
      }
    }
    return influencedPositions;
  }

  @Override
  public Card mirrorInfluence() {
    HashMap<InfluencePosition, InfluenceType> mirrored = new HashMap<>();
    for (InfluencePosition pos : this.influence.keySet()) {
      int posX = pos.getX() * -1;
      InfluencePosition mirroredPos = new InfluencePosition(posX, pos.getY());
      mirrored.put(mirroredPos, this.influence.get(pos));
    }
    return new PawnCard(this.name, this.cost, this.valueScore, mirrored);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other){
      return true;
    }
    if (!(other instanceof PawnCard)) {
      return false;
    } else {
      return this.cost == ((PawnCard) other).cost
              && this.valueScore == ((PawnCard) other).valueScore
              && this.name.equals(((PawnCard) other).name)
              && this.influence.equals(((PawnCard) other).influence);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.cost, this.valueScore, this.influence);
  }

  @Override
  public List<InfluencePosition> getUpgrades() {
    return List.of();
  }

  @Override
  public List<InfluencePosition> getDevalues() {
    return List.of();
  }
}
