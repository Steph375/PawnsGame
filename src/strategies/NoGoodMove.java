package strategies;

import model.PlayerColor;
import model.PawnsGameReadOnly;
import model.Card;
import java.util.List;

/**
 * Strategy that chooses the move that leaves their opponent in a situation with no good moves.
 */
public class NoGoodMove implements Strategies {

  /**
   * Creates a NoGoodMove strategy object.
   */
  public NoGoodMove() {
    // Strategy function object
  }

  @Override
  public Move chooseMove(PawnsGameReadOnly model) {
    PlayerColor player = model.getCurrentPlayer();
    PlayerColor opponent = (player == PlayerColor.RED) ? PlayerColor.BLUE : PlayerColor.RED;

    Move bestMove = null;
    int bestOpponentScore = Integer.MAX_VALUE; // We aim to minimize the opponent's advantage

    for (Card card : model.getCurrentPlayerHand()) {
      for (int row = 0; row < model.getHeight(); row++) {
        for (int col = 0; col < model.getWidth(); col++) {
          if (model.isLegalMove(row, col, card)) {
            int opponentScore = simulateOpponentMove(model, opponent);

            if (opponentScore < bestOpponentScore) {
              bestMove = new Move(card, row, col);
              bestOpponentScore = opponentScore;
            }
          }
        }
      }
    }

    return bestMove;

  }

  private int simulateOpponentMove(PawnsGameReadOnly model, PlayerColor opponent) {
    int worstCaseScore = 0;

    List<Card> opponentHand = (opponent == PlayerColor.RED)
            ? model.getPlayerRed().getHand()
            : model.getPlayerBlue().getHand();

    for (Card card : opponentHand) {
      for (int row = 0; row < model.getHeight(); row++) {
        for (int col = 0; col < model.getWidth(); col++) {
          if (model.isLegalMove(row, col, card)) {
            int[] rowScores = model.getRowScores(row);
            int opponentRowScore = (opponent == PlayerColor.RED) ? rowScores[0] : rowScores[1];

            if (opponentRowScore > worstCaseScore) {
              worstCaseScore = opponentRowScore;
            }
          }
        }
      }
    }

    return worstCaseScore;
  }
}