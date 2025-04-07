package view.console;

import java.io.IOException;
import java.util.ArrayList;
import model.Card;
import model.Pawn;
import model.Pawnsboard;
import model.PlayerTeam;
import view.PawnsBoardView;

/**
 * A class representing a textual view of the game of Pawns Board.
 */
public class PawnsBoardTextualView implements PawnsBoardView {

  Pawnsboard model;

  public PawnsBoardTextualView(Pawnsboard model) {
    this.model = model;
  }

  public void printBoard() {
    printBoard(System.out);
  }

  /**
   * prints the board state (of the model given in the constructor) to system out.
   */

  private void printBoard(Appendable appendable) {
    StringBuilder sb = new StringBuilder();
    ArrayList<Integer> leftScore = model.scoreRowsLeftPlayer();
    ArrayList<Integer> rightScore = model.scoreRowsRightPlayer();
    for (int i = 0; i < model.getRowSize(); i++) {
      sb.append(leftScore.get(i)).append(" ");
      for (int j = 0; j < model.getColSize(); j++) {

        Object piece = model.getBoardPiece(i, j);
        if (piece != null) {
          if (piece instanceof Pawn) {
            if (((Pawn) piece).getTeam() == PlayerTeam.RED) { //comment this out if the colors break
              sb.append(ConsoleColors.RED);
            } else {
              sb.append(ConsoleColors.BLUE);
            }
            sb.append(((Pawn) piece).getQuantity());
            sb.append(ConsoleColors.RESET);
          } else if (piece instanceof Card) {
            if (((Card) piece).getOwnerTeam() == PlayerTeam.RED) {
              // piece on board is a card, placed by left/red
              sb.append("R");
            } else {
              // piece on board is a card, placed by right/blu
              sb.append("B");
            }
          }
        } else {
          //blank spot
          sb.append("_");
        }

      }
      sb.append(" ").append(rightScore.get(i));
      sb.append("\n");
    }
    sb.append("--------\n");
    try {
      appendable.append(sb.toString());
    } catch (IOException ignored) {

    }
  }

  public void render(Appendable out) throws IOException {
    printBoard(out);
  }

  /**
   * Refreshes the current GUI view to reflect changes in the game state.
   */
  @Override
  public void updateView() {
    printBoard();
  }

  /**
   * Make the view Visible to start the game function.
   */
  @Override
  public void makeVisible() {
    printBoard();
  }
}
