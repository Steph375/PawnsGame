package controller;


import player.ActionPlayer;
import model.Card;
import model.IPlayer;
import model.PawnsGame;
import model.PlayerColor;
import view.PawnsView;

import javax.swing.JOptionPane;

/**
 * A controller to facilitate interactions between the external players through the GUI and the
 * game's model.
 */
public class GameController implements PawnsController, ViewActions, ModelListener {
  private final PawnsGame model;
  private final IPlayer player;
  private final ActionPlayer Aplayer;
  private final PawnsView view;
  private boolean isTurn;
  private int selectedCardIndex;
  private int selectedRow;
  private int selectedCol;

  /**
   * Creates a controller for the GUI of PawnsBoard.
   * @param model the game the controller is interacting with.
   * @param player the color of the player who's interacting with this controller.
   * @param aPlayer the external player that's playing the game through this controller.
   * @param view the GUI to display the game on and receive input from.
   */
  public GameController(PawnsGame model, PlayerColor player, ActionPlayer aPlayer, PawnsView view) {
    if (model == null || player == null || view == null) {
      throw new IllegalArgumentException("Arguments to controller cannot be null");
    }
    this.model = model;
    if (player == PlayerColor.RED) {
      this.player = this.model.getPlayerRed();
    } else {
      this.player = this.model.getPlayerBlue();
    }

    this.view = view;
    this.isTurn = false;
    this.selectedCardIndex = -1;
    this.selectedRow = -1;
    this.selectedCol = -1;
    this.Aplayer = aPlayer;
  }

  @Override
  public void playGame(PawnsGame model) {
    model.addModelListener(this);
    view.subscribe(this);
    view.makeVisible();
  }

  @Override
  public void onCardSelected(int index, PlayerColor color) {
    if (isTurn && color == player.getColor()) {
      selectedCardIndex = index;
    }
  }

  @Override
  public void onCellSelected(int row, int col) {
    if (isTurn) {
      selectedRow = row;
      selectedCol = col;
    }
  }

  @Override
  public void onConfirmMove() {
    if (!isTurn) {
      showError("It's not your turn.");
      return;
    }

    if (selectedCardIndex == -1 || selectedRow == -1 || selectedCol == -1) {
      showError("You must select a card and a cell before confirming.");
      return;
    }

    try {
      Card selectedCard = player.getHand().get(selectedCardIndex);
      model.placeCard(selectedRow, selectedCol, selectedCard);

      view.refresh();
    } catch (IllegalArgumentException | IllegalStateException e) {
      showError(e.getMessage());
    }
  }

  @Override
  public void onPassTurn() {
    if (!isTurn) {
      showError("It's not your turn.");
      return;
    }

    model.passTurn();

    view.refresh();
  }

  @Override
  public void onTurnChanged(PlayerColor currentPlayer) {

    this.isTurn = (currentPlayer == player.getColor());

    String title = "Player: " + player.getColor();
    if (isTurn) {
      title += " (Your Turn)";
    } else {
      title += " (Waiting...)";
    }
    view.setTitle(title);
    view.refresh();


    if (isTurn) {
      Aplayer.beginTurn(model, this);

    }

  }

  @Override
  public void onGameOver(PlayerColor winner, int redScore, int blueScore) {
    String message;
    if (winner == null) {
      message = "It's a tie!";  // No score displayed for a tie
    } else {
      message = "Winner: " + winner + "\n" + winner + " Score: ";
      // Display the winner's score
      if (winner == PlayerColor.RED) {
        message += redScore;
      } else {
        message += blueScore;
      }
    }
    JOptionPane.showMessageDialog(null, message);
    System.exit(0);
  }

  /**
   * Display an error message on the view using a showMessageDialog.
   * @param message the message to display.
   */
  private void showError(String message) {
    JOptionPane.showMessageDialog(null, message);
  }
}
