package controller;

import model.Move;

/**
 * Interface for ViewActions.
 */
public interface ViewActions {

  /**
   * method for handling mouse clicks on cells on a game board.
   *
   * @param row row of the mouse click.
   * @param col column of the mouse click.
   */
  void handleCellClick(int row, int col);

  /**
   * Method for handling the press of "Space" button on the keyboard during game.
   */
  void handleSpacePress();

  /**
   * Method for retrieving the move from the ViewActions.
   */
  Move getMove();
}
