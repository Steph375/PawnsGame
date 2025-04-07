package view;


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
}
