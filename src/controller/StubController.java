package controller;

import model.PawnsGame;
import model.PlayerColor;
import view.PawnsView;

/**
 * Controller for the view stub no actual moves.
 */
public class StubController implements PawnsController, ViewActions {

  private final PawnsView view;

  public StubController(PawnsView view) {
    this.view = view;
  }

  @Override
  public void playGame(PawnsGame model) {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }

    this.view.subscribe(this);
    this.view.makeVisible();
  }

  @Override
  public void onCardSelected(int index, PlayerColor player) {
    System.out.println("Card selected at index " + index + " by Player " + player);
  }

  @Override
  public void onCellSelected(int row, int col) {
    System.out.println("Cell selected at row " + row + " col " + col);
  }

  @Override
  public void onConfirmMove() {
    System.out.println("Move confirmed.");
  }

  @Override
  public void onPassTurn() {
    System.out.println("Turn passed.");
  }
}
