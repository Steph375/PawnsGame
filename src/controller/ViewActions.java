package controller;

import model.PlayerColor;

/**
 * The observer that handles user input from the GUI.
 */
public interface ViewActions {
  /**
   * Called when a card is selected in the hand panel.
   *
   * @param index  The index of the selected card.
   * @param player The player whose hand the card belongs to.
   */
  void onCardSelected(int index, PlayerColor player);

  /**
   * Called when a board cell is selected.
   *
   * @param row The row index of the selected cell.
   * @param col The column index of the selected cell.
   */
  void onCellSelected(int row, int col);

  /**
   * Called when the user confirms a move by pressing 'C'.
   */
  void onConfirmMove();

  /**
   * Called when the user passes their turn by pressing 'P'.
   */
  void onPassTurn();
}
