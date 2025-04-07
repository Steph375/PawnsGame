package player.computer;

import model.Move;
import model.PlayerTeam;
import model.ReadonlyPawnsBoard;

/**
 * A class representing a hybrid strategy for the computer.
 */
public class HybridStrategy implements Strategy {

  /**
   * When called, this function will return a new move object, detailing the moves the player would
   * like to make. The model class (Pawnsboard) will be passed into the constructor and the player
   * can use this to gather information (for example for an ai player) to determine the next move.
   *
   * @param team  Player team of the Computer.
   * @param board current board of the PawnsBoard game.
   */
  @Override
  public Move makeMove(PlayerTeam team, ReadonlyPawnsBoard board) {
    return null;
  }
}
