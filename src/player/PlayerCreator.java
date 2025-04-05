package player;

import model.PlayerColor;
import strategies.ControlBoard;
import strategies.FillFirst;
import strategies.MaximizeScore;
import strategies.MultipleStrategies;
import strategies.NoGoodMove;
import strategies.Strategies;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory class for building a ActionPlayer of the given color and type (human or machine using
 * the given Strategy).
 */
public class PlayerCreator {

  /**
   * Creates an ActionPlayer using the given specifications.
   * @param color the color of this player.
   * @param type either 'human' or the strategy used by a machine player e.g. 'strategy1'
   *             'strategy2' or for MultipleStrategies 'strategy3, strategy1'.
   * @return an ActionPlayer of the specified type and color.
   */
  public static ActionPlayer build(PlayerColor color, String type) {
    if (type == null) {
      throw new IllegalArgumentException("Player type cannot be null.");
    }

    String lowered = type.toLowerCase().trim();

    if (lowered.equals("human")) {
      return new HumanPlayer();
    }

    String[] parts = lowered.split(",");
    List<Strategies> strategies = new ArrayList<>();

    for (String name : parts) {
      switch (name.trim()) {
        case "strategy1":
          strategies.add(new FillFirst());
          break;
        case "strategy2":
          strategies.add(new ControlBoard());
          break;
        case "strategy3":
          strategies.add(new MaximizeScore());
          break;
        case "strategy4":
          strategies.add(new NoGoodMove());
          break;
        default:
          throw new IllegalArgumentException("Unknown strategy: " + name);
      }
    }

    if (strategies.isEmpty()) {
      throw new IllegalArgumentException("At least one strategy must be provided.");
    }

    Strategies combined = (strategies.size() == 1)
            ? strategies.get(0)
            : new MultipleStrategies(strategies);

    return new MachinePlayer(color, combined);
  }
}
