package controller;

import model.PawnsGame;

/**
 * Represents a Controller for the PawnsBoard game.
 */
public interface PawnsController {


  /**
   * Execute a single game of PawnsBoard given a PawnsBoard Model.
   *
   * @param model a non-null PawnsBoard Model.
   */
  void playGame(PawnsGame model);
}
