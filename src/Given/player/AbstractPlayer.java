package player;

import controller.PawnsBoardController;
import model.Move;
import model.PlayerTeam;
import model.ReadonlyPawnsBoard;

/**
 * Just holds some simple helper methods shared for human and cpu players.
 */
public abstract class AbstractPlayer implements Player {

  protected PlayerTeam team;
  protected ReadonlyPawnsBoard model;
  protected PawnsBoardController controller;
  protected Boolean gameOver = false;

  public AbstractPlayer(ReadonlyPawnsBoard model, PlayerTeam team) {
    this.model = model;
    this.team = team;
  }

  /**
   * Returns the team the player is on. Either red or blue, and set via the constructor
   *
   * @return the team the player is on
   */
  @Override
  public PlayerTeam getColor() {
    return this.team;
  }

  @Override
  public void setController(PawnsBoardController controller) {
    this.controller = controller;
  }

  @Override
  public void submitMove(Move move) {
    if (controller != null && !gameOver) {
      controller.makeMove(move, team);
    }
  }

  @Override
  public void notifyGameOver() {
    gameOver = true;
    this.notifyGameChanged();
  }
}
