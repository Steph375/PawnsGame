package controller;

import model.PlayerColor;

public interface ModelListener {
  void onTurnChanged(PlayerColor currentPlayer);
  void onGameOver(PlayerColor winner, int redScore, int blueScore);
}

