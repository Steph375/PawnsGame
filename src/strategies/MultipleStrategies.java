package strategies;

import java.util.List;

import model.PawnsGameReadOnly;

/**
 * Uses a list to implement multiple stratieges in order.
 */
public class MultipleStrategies implements Strategies {
  private final List<Strategies> strategies;

  /**
   * Constructor that will take in the list of strategies to go in each on.
   * @param strategies list of the strategies we made
   */
  public MultipleStrategies(List<Strategies> strategies) {
    if (strategies == null || strategies.isEmpty()) {
      throw new IllegalArgumentException("Strategy list cant be null or empty.");
    }
    if (strategies.size() < 2) {
      throw new IllegalArgumentException("Strategy list must contain at least two strategies.");
    }
    this.strategies = strategies;
  }

  @Override
  public Move chooseMove(PawnsGameReadOnly model) {
    for (Strategies strategy : strategies) {
      Move move = strategy.chooseMove(model);
      if (move != null) {
        return move;
      }
    }
    return null;
  }
}

