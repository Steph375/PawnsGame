package controller;

import model.PlayerColor;
import strategies.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds players based on a type string ("human", "strategy1", "strategy1,strategy3").
 */
public class PlayerBuilder {

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
