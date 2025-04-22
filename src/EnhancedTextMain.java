import java.io.File;
import java.util.ArrayList;
import java.util.List;

import controller.EnhancedDeckReader;
import model.BoardCell;
import model.Card;
import model.EnhancedPawnsGame;
import model.PawnsGame;
import model.PlayerColor;
import view.PawnsTextualView;
import view.TextualView;

/**
 * Plays out a full enhanced game using influence cards.
 */
public class EnhancedTextMain {
  /**
   * Main method for enhanced text game.
   * @param args imputs for the game
   */
  public static void main(String[] args) {
    String path = "docs" + File.separator + "enhanced.config";
    File configFile = new File(path);

    // Read enhanced deck with U/D influences.
    List<Card> deck = EnhancedDeckReader.readDeck(configFile);

    PawnsGame model = new EnhancedPawnsGame(3, 5);
    model.setupGame(new ArrayList<>(deck), new ArrayList<>(deck), 5, false);

    TextualView view = new PawnsTextualView(model);

    while (!model.isGameOver()) {
      System.out.println(view);

      boolean moveMade = false;
      List<Card> hand = model.getCurrentPlayerHand();
      BoardCell[][] board = model.getBoard();

      for (int r = 0; r < board.length && !moveMade; r++) {
        for (int c = 0; c < board[r].length && !moveMade; c++) {
          for (Card card : hand) {
            if (model.isLegalMove(r, c, card)) {
              model.placeCard(r, c, card);
              moveMade = true;
              break;
            }
          }
        }
      }

      if (!moveMade) {
        model.passTurn();
      }

      model.drawCard();
    }

    System.out.println(view);
    PlayerColor winner = model.determineWinner();
    System.out.println("Game Over! " + (winner == null ? "Tie!" : "Winner: " + winner));
  }
}
