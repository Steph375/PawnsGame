package player;

import java.util.List;

import controller.ViewActions;
import model.Card;
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
  public void beginTurn(PawnsGameReadOnly model, ViewActions observer) {
    // Only proceed if it's the machine player's turn
    if (model.getCurrentPlayer() != color) {
      observer.onPassTurn();
      return;
    }

    Move move = strategy.chooseMove(model);

    if (move == null || move.getCard() == null) {
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
      observer.onPassTurn();
      return;
    }

    observer.onCardSelected(index, color);
    observer.onCellSelected(move.getRow(), move.getCol());
    observer.onConfirmMove();
  }

}
