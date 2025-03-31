package controller;

import java.util.List;

import model.Card;
import model.PawnsGame;
import model.PawnsGameReadOnly;
import model.PlayerColor;
import strategies.Move;
import strategies.Strategies;

public class MachinePlayer implements ActionPlayer {
  private final PlayerColor color;
  private final Strategies strategy;

  public MachinePlayer(PlayerColor color, Strategies strategy) {
    this.color = color;
    this.strategy = strategy;
  }

  @Override
  public void beginTurn(PawnsGame model, ViewActions observer) {
    // Only proceed if it's the machine player's turn
    if (model.getCurrentPlayer() != color) {
      System.out.println("It's not the machine player's turn.");
      observer.onPassTurn();
      return;
    }

    // Machine draws a card only on its turn
    model.drawCard();  // Ensure the correct player draws a card on their turn.

    System.out.println("Machine player starting turn...");
    Move move = strategy.chooseMove(model);

    if (move == null || move.getCard() == null) {
      System.out.println("Machine has no move, passing...");
      observer.onPassTurn();
      return;
    }

    List<Card> hand = model.getCurrentPlayerHand();
    int index = -1;
    for (int i = 0; i < hand.size(); i++) {
      if (hand.get(i) == move.getCard()) {
        index = i;
        break;
      }
    }

    if (index == -1) {
      System.out.println("Card not found in hand, passing...");
      observer.onPassTurn();
      return;
    }

    System.out.println("Machine playing card index " + index + " at " +
            move.getRow() + "," + move.getCol());

    observer.onCardSelected(index, color);
    observer.onCellSelected(move.getRow(), move.getCol());
    observer.onConfirmMove();
  }

}
