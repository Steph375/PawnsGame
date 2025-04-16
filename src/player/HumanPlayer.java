package player;

import controller.ViewActions;

import model.PawnsGameReadOnly;



/**
 * An automated player of the PawnsBoard.
 */
public class HumanPlayer implements ActionPlayer {

  public HumanPlayer() {
    // does not contain info, because input comes from a real user
  }

  @Override
  public void beginTurn(PawnsGameReadOnly model, ViewActions observer) {
    // input comes directly from the user via the GUI
  }
}
