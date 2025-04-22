package provider.controller;


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
   * Ends the game.
   */
  void gameOver();

  /**
   * Selects a card in hand.
   * @param id index of the card in hand.
   */
  void setSelectedCard(int id);
}
