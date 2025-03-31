package controller;


import model.Card;
import model.IPlayer;
import model.PawnsGame;
import model.PlayerColor;
import view.PawnsView;

import javax.swing.JOptionPane;

public class GameController implements PawnsController, ViewActions, ModelListener {
  private final PawnsGame model;
  private final IPlayer player;
  private final ActionPlayer Aplayer;
  private final PawnsView view;

  private boolean isTurn;
  private int selectedCardIndex;
  private int selectedRow;
  private int selectedCol;

  public GameController(PawnsGame model, IPlayer player, PawnsView view, String type) {
    if (model == null || player == null || view == null) {
      throw new IllegalArgumentException("Arguments to controller cannot be null");
    }
    this.model = model;
    this.player = player;
    this.view = view;
    this.isTurn = false;
    this.selectedCardIndex = -1;
    this.selectedRow = -1;
    this.selectedCol = -1;

  }

  @Override
  public void playGame(PawnsGame model) {
    model.addModelListener(this);  // Listen to turn/game status from the model
    view.subscribe(this);          // Listen to user inputs from the view
    view.makeVisible();            // Show the GUI
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
      model.drawCard();
      clearSelections();
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
    model.drawCard();
    clearSelections();
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
      player.beginTurn(model, this);
    }
    System.out.println("Turn changed to: " + currentPlayer);

  }



  @Override
  public void onGameOver(PlayerColor winner, int redScore, int blueScore) {
    String message;
    if (winner == null) {
      message = "It's a tie!";
    } else {
      message = "Winner: " + winner;
    }
    message += "\nRed Score: " + redScore + ", Blue Score: " + blueScore;
    JOptionPane.showMessageDialog(null, message);
    System.exit(0);
  }

  private void clearSelections() {
    selectedCardIndex = -1;
    selectedRow = -1;
    selectedCol = -1;
  }

  private void showError(String message) {
    JOptionPane.showMessageDialog(null, message);
  }
}
