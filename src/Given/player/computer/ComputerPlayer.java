package player.computer;

import model.Move;
import model.PlayerTeam;
import model.ReadonlyPawnsBoard;
import player.AbstractPlayer;

/**
 * A class representing a computer player.
 */
public class ComputerPlayer extends AbstractPlayer {

  Strategy strategy;

  /**
   * constructor for the Computer player.
   *
   * @param team     team of the computer player.
   * @param model    model of the Pawnsboard game.
   * @param strategy strategy that Computer uses.
   */
  public ComputerPlayer(PlayerTeam team, ReadonlyPawnsBoard model, Strategy strategy) {
    super(model, team);
    this.strategy = strategy;
  }

  /**
   * signal to the player the game has changed, and it might need to take action.
   */
  @Override
  public void notifyGameChanged() {
    if (model.getNextTurn().equals(team) && controller != null) {
      Move move = strategy.makeMove(team, model);
      this.submitMove(move);
    }
  }
}
