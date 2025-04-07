package player.computer;

import model.Move;
import model.PlayerTeam;
import model.ReadonlyPawnsBoard;

/**
 * A strategy interface.
 */
public interface Strategy {

  /**
   * When called, this function will return a new move object, detailing the moves the player would
   * like to make. The model class (Pawnsboard) will be passed into the constructor and the player
   * can use this to gather information (for example for an ai player) to determine the next move.
   */
  Move makeMove(PlayerTeam team, ReadonlyPawnsBoard board);

}
