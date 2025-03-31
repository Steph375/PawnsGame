package strategies;

import model.BoardCell;
import model.Card;
import model.PawnsGameReadOnly;
import model.PlayerColor;

/**
 * choose a card and location that will give the current player ownership of the most cells.
 * In a tie between positions, choose the uppermost-leftmost (so uppermost first, then leftmost).
 * In a tie between cards, choose the leftmost (or closest to first) card.
 */
public class ControlBoard implements Strategies {

  /**
   * Empty constructor to call class.
   */
  public ControlBoard() {
    //empty
  }

  @Override
  public Move chooseMove(PawnsGameReadOnly model) {
    PlayerColor player = model.getCurrentPlayer();
    Move bestMove = null;
    int maxCellsOwned = 0;

    for (Card card : model.getCurrentPlayerHand()) {
      for (int row = 0; row < model.getHeight(); row++) {
        for (int col = 0; col < model.getWidth(); col++) {
          if (model.isLegalMove(row, col, card)) {
            int cellsOwned = countOwnedCells(model, player);

            if (cellsOwned > maxCellsOwned || (cellsOwned == maxCellsOwned
                    && isBetterTieBreaker(row, col, bestMove, player))) {
              bestMove = new Move(card, row, col);
              maxCellsOwned = cellsOwned;
            }
          }
        }
      }
    }

    return bestMove;
  }

  private int countOwnedCells(PawnsGameReadOnly model, PlayerColor player) {
    int ownedCells = 0;
    BoardCell[][] board = model.getBoard();
    for (int r = 0; r < model.getHeight(); r++) {
      for (int c = 0; c < model.getWidth(); c++) {
        if (board[r][c].getColor() == player) {
          ownedCells++;
        }
      }
    }
    return ownedCells;
  }

  private boolean isBetterTieBreaker(int row, int col, Move bestMove, PlayerColor player) {
    if (bestMove == null) {
      return true;
    }

    if (row < bestMove.getRow()) {
      return true;
    }

    if (row == bestMove.getRow()) {
      if (player == PlayerColor.RED) {
        return col < bestMove.getCol();
      } else {
        return col > bestMove.getCol();
      }
    }

    return false;
  }
}
