package player;

import controller.ViewActions;
import model.PawnsGameReadOnly;

/**
 * This interface represents an external player that interacts with the game.
 */
public interface ActionPlayer {
  /**
   * Start the turn of this player.
   * @param model the game they're playing a turn in.
   * @param observer the controller that handles input from the GUI.
   */
  void beginTurn(PawnsGameReadOnly model, ViewActions observer);
}
