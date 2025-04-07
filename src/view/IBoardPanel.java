package view;

import controller.ViewActions;

/**
 * Represents the panel in a PawnsView GUI that visualizes the game's board.
 */
public interface IBoardPanel {

  /**
   * Connects the controller.ViewActions observer that handles user input to this panel.
   * @param observer the controller.ViewActions object that responds to user input.
   */
  void subscribe(ViewActions observer);


}
