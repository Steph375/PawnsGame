import java.io.File;
import java.util.ArrayList;
import java.util.List;

import controller.EnhancedDeckReader;
import model.BoardCell;
import model.Card;
import model.EnhancedPawnsGame;
import model.PawnsGame;
import model.PlayerColor;
import view.EnhancedTextualView;
import view.TextualView;

/**
 * Plays out a full enhanced game using influence cards.
 */
public class EnhancedTextMain {
  /**
   * Main method for enhanced text game.
   * @param args inputs for the game
   */
  public static void main(String[] args) {
    String path = "docs" + File.separator + "enhanced.config";
    File configFile = new File(path);

    List<Card> fullDeck = EnhancedDeckReader.readDeck(configFile);
    List<Card> redDeck = new ArrayList<>(fullDeck);
    List<Card> blueDeck = new ArrayList<>(fullDeck);

    PawnsGame model = new EnhancedPawnsGame(3, 5);
    model.setupGame(redDeck, blueDeck, 5, false);

    TextualView view = new EnhancedTextualView(model);

    // moves to demonstrate card removal and canceling upgrade/devalue effects
    model.placeCard(0, 0, model.getCurrentPlayerHand().get(0)); // RED
    model.drawCard();
    System.out.println(view + "\n");

    model.placeCard(0, 4, model.getCurrentPlayerHand().get(0)); // BLUE (to be removed)
    model.drawCard();
    System.out.println(view + "\n");

    model.placeCard(0, 1, model.getCurrentPlayerHand().get(0)); // RED
    model.drawCard();
    System.out.println(view + "\n");

    model.placeCard(1, 4, model.getCurrentPlayerHand().get(0)); // BLUE devalues top-right
    model.drawCard();
    System.out.println(view + "\n");

    model.placeCard(1, 2, model.getCurrentPlayerHand().get(0)); // RED cancels with upgrade
    model.drawCard();
    System.out.println(view + "\n");

    // loop through remaining moves
    while (!model.isGameOver()) {
      System.out.println(view + "\n");

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
    System.out.println("\nGame Over! " + (winner == null ? "Tie!" : "Winner: " + winner));
  }
}
