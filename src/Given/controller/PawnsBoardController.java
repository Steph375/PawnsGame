package controller;


import model.ModelListener;
import model.Move;
import model.PawnsboardImpl;
import model.PlayerTeam;
import player.Player;

/**
 * A class representing the controller for the game.
 */
public class PawnsBoardController implements ModelListener {

  private final PawnsboardImpl model;
  private final Player player;

  /**
   * Constructor for the controller.
   * @param model  the model to use
   * @param player the player to use
   */
  public PawnsBoardController(PawnsboardImpl model, Player player) {
    this.model = model;
    model.addListener(this);
    this.player = player;
  }

  public void playGame() {
    player.setController(this);
    player.notifyGameChanged();
  }

  /**
   * the player object uses this to tell the controller it wants to move.
   *
   * @param move the move to make
   * @param team which team the player is
   * @throws IllegalStateException if it is not their turn
   */
  public void makeMove(Move move, PlayerTeam team) {
    if (model.isGameOver()) {
      player.notifyGameOver();
    }
    if (model.getNextTurn() == team) {
      model.makeMove(move);
    } else {
      throw new IllegalStateException("player tried to make a move while it is not their turn");
    }
    if (model.isGameOver()) { // that move might have caused the game to end, so check that
      player.notifyGameOver();
    }
  }

  @Override
  public void onModelChanged() {
    player.notifyGameChanged();
  }
}
