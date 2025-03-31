package view;

import controller.ViewActions;

/**
 * Represents a GUI for the game PawnsBoard.
 */
public interface PawnsView {
  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * Connects the ViewActions object that handles user input to this panel.
   * @param observer the ViewActions object that handles user input.
   */
  void subscribe(ViewActions observer);

  void setTitle(String title);
}
