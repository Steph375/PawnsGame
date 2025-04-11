package provider.controller;

import provider.model.Move;
import provider.model.PlayerTeam;

public interface Controller {
  /**
   * method for playing the game.
   */
  void playGame();

  /**
   * the player object uses this to tell the controller it wants to move.
   *
   * @param move the move to make
   * @param team which team the player is
   * @throws IllegalStateException if it is not their turn
   */
  void makeMove(Move move, PlayerTeam team);
}
