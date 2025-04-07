package player;

import model.PlayerTeam;
import model.ReadonlyPawnsBoard;
import view.graphical.GUIView;
import view.graphical.actions.HumanViewActions;

/**
 * A human player. manages the view for the player, and is how the view and controller communicate.
 */
public class HumanPlayer extends AbstractPlayer {
  GUIView view;
  HumanViewActions viewActions;

  /**
   * Constructor for the human player.
   *
   * @param team  which team they play as
   * @param board the model for the board, for the view to display the state
   */
  public HumanPlayer(PlayerTeam team, ReadonlyPawnsBoard board) {
    super(board, team);

    this.view = new GUIView(board, team);
    this.viewActions = new HumanViewActions(board, this, view, team);

    view.setActionsListener(viewActions);
    view.update();
    view.makeVisible();
  }

  /**
   * Tell the player that the game state has changed and to change the view.
   */
  public void notifyGameChanged() {
    if (gameOver) {
      view.gameOver(super.model.getWinner());
    }
    view.update();
  }
}
