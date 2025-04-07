package view.graphical.actions;


import controller.ViewActions;
import model.Move;
import model.MoveType;
import model.PlayerTeam;
import model.ReadonlyPawnsBoard;
import player.Player;
import view.graphical.GUIView;

/**
 * This class handles the view doing stuff- the other classes are for the visual display of the
 * view. it holds action listeners.
 */
public class HumanViewActions implements ViewActions {

  private final ReadonlyPawnsBoard model;
  private final GUIView view;
  private final PlayerTeam team;
  private final Player player;
  private Integer selectedHandIdx = null;
  private Boolean isMyTurn;
  private Boolean gameOver = false;

  /**
   * Constructor for this class :).
   *
   * @param model  the model to use
   * @param player the player object this will communicate with.
   * @param view   the view to listen to
   * @param team   which team this is
   */
  public HumanViewActions(ReadonlyPawnsBoard model, Player player, GUIView view, PlayerTeam team) {
    this.model = model;
    this.player = player;
    this.view = view;
    this.team = team;
  }

  public void setSelectedCard(int id) {
    this.selectedHandIdx = id;
  }

  @Override
  public void handleCellClick(int row, int col) {
    if (gameOver) {
      view.warnPlayer("Game over!");
      return;
    }

    if (isItMyTurn()) {
      try {
        if (selectedHandIdx == null) {
          view.warnPlayer("select a card first");
          return;
        }
        Move move = new Move(MoveType.PLACE);
        move.placeRow = row;
        move.placeCol = col;
        move.handIdx = selectedHandIdx;
        player.submitMove(move);
        view.clearSelection();
      } catch (IllegalArgumentException e) {
        view.warnPlayer("that was not a legal move");
      }
      view.updateTurnLabel();
      view.updateHand(this);
      view.updateScores();
      view.updateView();
      view.clearSelection();
      selectedHandIdx = null;
    } else {
      view.warnPlayer("it is not your turn");
    }
  }


  @Override
  public void handleSpacePress() {
    if (gameOver) {
      view.warnPlayer("Game over!");
      return;
    }

    if (isItMyTurn()) {
      Move move = new Move(MoveType.PASS);
      player.submitMove(move);
      view.updateTurnLabel();
      view.updateHand(this);
      view.updateScores();
      view.updateView();
      view.clearSelection();
    } else {
      view.warnPlayer("it is not your turn");
    }
  }

  private Boolean isItMyTurn() {
    return model.getNextTurn() == team;
  }

  public void gameOver() {
    this.gameOver = true;
  }

}
