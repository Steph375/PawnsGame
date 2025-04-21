package model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A subclass of PawnCard that exposes UPGRADE and DEVALUE influence information separately.
 */
public class InfluencePawnCard extends PawnCard {

  public InfluencePawnCard(String name, int cost, int valueScore,
                           Map<InfluencePosition, InfluenceType> influence) {
    super(name, cost, valueScore, influence);
  }

  /**
   * Returns only STANDARD influence positions.
   */
  @Override
  public List<InfluencePosition> getInfluence() {
    List<InfluencePosition> result = new ArrayList<>();
    for (Map.Entry<InfluencePosition, InfluenceType> entry : super.influence.entrySet()) {
      if (entry.getValue() == InfluenceType.INFLUENCE) {
        result.add(entry.getKey());
      }
    }
    return result;
  }

  /**
   * Returns all influence positions that are UPGRADE type.
   */
  @Override
  public List<InfluencePosition> getUpgrades() {
    List<InfluencePosition> result = new ArrayList<>();
    for (Map.Entry<InfluencePosition, InfluenceType> entry : super.influence.entrySet()) {
      if (entry.getValue() == InfluenceType.UPGRADE) {
        result.add(entry.getKey());
      }
    }
    return result;
  }

  /**
   * Returns all influence positions that are DEVALUE type.
   */
  @Override
  public List<InfluencePosition> getDevalues() {
    List<InfluencePosition> result = new ArrayList<>();
    for (Map.Entry<InfluencePosition, InfluenceType> entry : super.influence.entrySet()) {
      if (entry.getValue() == InfluenceType.DEVALUE) {
        result.add(entry.getKey());
      }
    }
    return result;
  }

  @Override
  public Card mirrorInfluence() {
    Map<InfluencePosition, InfluenceType> mirrored = new HashMap<>();
    for (Map.Entry<InfluencePosition, InfluenceType> entry : super.influence.entrySet()) {
      InfluencePosition pos = entry.getKey();
      InfluenceType type = entry.getValue();
      // mirror x
      InfluencePosition mirroredPos = new InfluencePosition(-pos.getX(), pos.getY());
      mirrored.put(mirroredPos, type);
    }
    return new InfluencePawnCard(getName(), getCost(), getValueScore(), mirrored);
  }


}

