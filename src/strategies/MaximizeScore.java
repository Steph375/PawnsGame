package strategies;


import model.Card;
import model.PawnsGameReadOnly;
import model.PlayerColor;

/**
 * Choose a card and location that will allow the current player to win a particular row by
 * making their row-score higher than the opponent’s row-score. Rows are visited from
 * top-down. If the current player has a lower or equal row-score than their opponent
 * on that row, this strategy chooses the first card and location option that increases
 * their row-score to be greater than or equal to the opponent’s row-score.
 */
public class MaximizeScore implements Strategies {

  /**
   * empty constructor to call method.
   */
  public MaximizeScore() {
    //empty
  }

  @Override
  public Move chooseMove(PawnsGameReadOnly model) {
    PlayerColor player = model.getCurrentPlayer();
    Move bestMove = null;
    int bestGain = Integer.MIN_VALUE;

    for (Card card : model.getCurrentPlayerHand()) {
      for (int row = 0; row < model.getHeight(); row++) {
        int[] rowScores = model.getRowScores(row);
        int playerScore = (player == PlayerColor.RED) ? rowScores[0] : rowScores[1];
        int opponentScore = (player == PlayerColor.RED) ? rowScores[1] : rowScores[0];

        for (int col = 0; col < model.getWidth(); col++) {
          if (model.isLegalMove(row, col, card)) {
            int newScore = playerScore + card.getValueScore();
            if (newScore > opponentScore) {
              if (newScore > bestGain) {
                bestGain = newScore;
                bestMove = new Move(card, row, col);
              }
            }
          }
        }
      }
    }

    return bestMove;
  }
}