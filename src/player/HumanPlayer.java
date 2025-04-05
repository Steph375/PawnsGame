package player;

import controller.ViewActions;
import model.PawnsGameReadOnly;

/**
 * An external human player of PawnsBoard.
 */
public class HumanPlayer implements ActionPlayer {
  @Override
  public void beginTurn(PawnsGameReadOnly model, ViewActions observer) {
    // Does nothing because human input will come directly from interactions with the GUI.
  }
}
