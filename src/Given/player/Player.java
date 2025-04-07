package player;

import controller.PawnsBoardController;
import model.Move;
import model.PlayerTeam;

/**
 * Interface representing a player in a game of Pawns Board.
 */
public interface Player {

  /**
   * signal to the player the game has changed, and it might need to take action.
   */
  void notifyGameChanged();

  /**
   * Returns the team the player is on. Either red or blue, and set via the constructor
   *
   * @return the team the player is on
   */
  PlayerTeam getColor();

  void setController(PawnsBoardController controller);

  void submitMove(Move move);

  void notifyGameOver();
}
