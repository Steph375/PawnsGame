package AdapterStuff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import model.Card;
import model.InfluencePosition;
import model.PawnCard;

public class ProviderCardToPawnsCard implements Card {
  provider.model.Card card;

  public ProviderCardToPawnsCard(provider.model.Card card) {
    this.card = card;
  }

  @Override
  public int getValueScore() {
      return this.card.value;
  }

  @Override
  public int getCost() {
    return this.card.cost;
  }

  @Override
  public List<InfluencePosition> getInfluence() {
    ArrayList<InfluencePosition> positionList = new ArrayList<InfluencePosition>();

    for(int row = 0; row < this.card.influence.length; row++) {
      for (int col = 0; col < this.card.influence[row].length; col++) {
        if (this.card.influence[row][col] == 0) {
          // add/subtract to convert to the coordinate system we use for InfluencePosition
          positionList.add(new InfluencePosition(col - 2, (-1 * row) + 2));
        }
      }
    }
    return positionList;
  }

  @Override
  public Card mirrorInfluence() {
    Integer[][] newInfluence = new Integer[5][5];
    List<InfluencePosition> influence = this.getInfluence();

    // fill the array with zeros
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        newInfluence [row][col] =  0;
      }
    }

    // replace mirrored positions that have influence with 1
    for (InfluencePosition pos : influence) {
      int mirroredX = pos.getX() * -1;
      newInfluence[mirroredX + 2][(-1 *pos.getY()) -2] = 1;
    }
    HashMap<InfluencePosition, Boolean> influenceMap = new HashMap<>();
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        boolean hasInfluence = newInfluence[row][col] == 1;
        if (hasInfluence) {
          InfluencePosition ip = new InfluencePosition(row - 2, col - 2);
          influenceMap.put(ip, true);
        }
      }
    }
   return new PawnCard(this.card.name, this.card.cost, this.card.value, influenceMap);
  }

  @Override
  public String getName() {
    return this.card.name;
  }
}
