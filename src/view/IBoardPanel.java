package view;

import controller.ViewActions;

/**
 * Represents the panel in a PawnsView GUI that visualizes the game's board.
 */
public interface IBoardPanel {

  /**
   * Connects the ViewActions observer that handles user input to this panel.
   * @param observer the ViewActions object that responds to user input.
   */
  void subscribe(ViewActions observer);


}
