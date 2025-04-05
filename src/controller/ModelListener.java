package controller;

import model.PlayerColor;

/**
 * An Observer interface that reacts to changes in the model.
 */
public interface ModelListener {

  /**
   * Responds to turn being changed in the model.
   * @param currentPlayer the color of the player whose turn it now is.
   */
  void onTurnChanged(PlayerColor currentPlayer);

  /**
   * Responds to the game ending.
   * @param winner the color of the player who won.
   * @param redScore the score of the red player.
   * @param blueScore the score of the blue player.
   */
  void onGameOver(PlayerColor winner, int redScore, int blueScore);
}

