package model;

import java.util.List;

public class EnhancedPawnsGame extends PawnsGameModel {

  public EnhancedPawnsGame(int rows, int cols) {
    super(rows, cols);
  }

  @Override
  protected void initializeBoard() {
    BoardCell[][] newBoard = new BoardCell[getHeight()][getWidth()];
    for (int r = 0; r < getHeight(); r++) {
      for (int c = 0; c < getWidth(); c++) {
        if (c == 0) {
          newBoard[r][c] = new EnhancedCell(1, null, PlayerColor.RED);
        } else if (c == getWidth() - 1) {
          newBoard[r][c] = new EnhancedCell(1, null, PlayerColor.BLUE);
        } else {
          newBoard[r][c] = new EnhancedCell(0, null, null);
        }
      }
    }
    this.board = newBoard;
  }

  @Override
  protected void applyCardInfluence(int row, int col, Card card) {
    PlayerColor player = getCurrentPlayer();

    for (InfluencePosition offset : card.getInfluence()) {
      int r = row - offset.getY();
      int c = col + offset.getX();
      if (isOnBoard(r, c)) {
        this.board[r][c].addPawns(1, player);
      }
    }

    for (InfluencePosition offset : card.getUpgrades()) {
      int r = row - offset.getY();
      int c = col + offset.getX();
      if (isOnBoard(r, c)) {
        this.board[r][c].applyUpgrade();
      }
    }

    for (InfluencePosition offset : card.getDevalues()) {
      int r = row - offset.getY();
      int c = col + offset.getX();
      if (isOnBoard(r, c)) {
        this.board[r][c].applyDevalue();
      }
    }
  }


  @Override
  public int[] getRowScores(int row) {
    int redScore = 0;
    int blueScore = 0;

    for (int c = 0; c < getWidth(); c++) {
      BoardCell cell = board[row][c]; // Use the raw field to avoid copy
      Card card = cell.getCard();

      if (card != null) {
        int raw = card.getValueScore();
        int bonus = cell.getUpgrade() - cell.getDevalue();
        int adjusted = Math.max(0, raw + bonus);

        if (adjusted == 0) {
             cell.clearCard(); // or setCard(null)
            cell.resetModifiers();

        } else if (cell.getColor() == PlayerColor.RED) {
          redScore += adjusted;
        } else if (cell.getColor() == PlayerColor.BLUE) {
          blueScore += adjusted;
        }
      }
    }

    return new int[]{redScore, blueScore};
  }


  private boolean isOnBoard(int row, int col) {
    return row >= 0 && row < getHeight() && col >= 0 && col < getWidth();
  }

  @Override
  public BoardCell[][] getBoard() {
    return this.board;
  }

}
