package controller;

import model.Card;
import model.PawnsGameReadOnly;
import model.PlayerColor;
import strategies.Move;
import strategies.Strategies;

import java.util.ArrayList;
import java.util.List;

public class MachinePlayer implements ActionPlayer {
  private final PlayerColor color;
  private final List<Card> deck;
  private final List<Card> hand;
  private final Strategies strategy;
  private ViewActions observer;

  public MachinePlayer(PlayerColor color, List<Card> deck, int handSize, Strategies strategy) {
    this.color = color;
    this.strategy = strategy;
    this.deck = new ArrayList<>(deck);
    this.hand = new ArrayList<>();
    for (int i = 0; i < handSize; i++) {
      drawCard();
    }
  }

  public void setObserver(ViewActions observer) {
    this.observer = observer;
  }


  @Override
  public void beginTurn(PawnsGameReadOnly model, ViewActions observer) {
    System.out.println("Machine player starting turn...");
    Move move = strategy.chooseMove(model);

    if (move == null || move.getCard() == null) {
      System.out.println("Machine has no move, passing...");
      observer.onPassTurn();
      return;
    }

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
