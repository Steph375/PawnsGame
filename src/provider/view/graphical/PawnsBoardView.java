package provider.view.graphical;


import provider.controller.ViewActions;
import provider.model.PlayerTeam;

/**
 * A view interface for the game of Pawnsboard.
 */
public interface PawnsBoardView {

  /**
   * Refreshes the current GUI view to reflect changes in the game state.
   */
  void updateView();

  /**
   * Make the view Visible to start the game function.
   */
  void makeVisible();

  void setActionsListener(ViewActions actions);

  /**
   * Updates the current visualization of the hand.
   */
  void updateHand(ViewActions viewActions);

  /**
   * Clears the selection of cells.
   */
  void clearSelection();

  /**
   * Updates the label of whose turn is it.
   */
  void updateTurnLabel();

  /**
   * Method for updating the scores of the game.
   */
  void updateScores();

  /**
   * Convenience method that calls all the other update methods. NOTE: updateHand is called with
   * the last viewactions object, but this (probably) shouldnt ever change.
   */
  void update();

  void warnPlayer(String s);

  /**
   * Makes the view tell the player the game is over.
   */
  void gameOver(PlayerTeam winner);
}


