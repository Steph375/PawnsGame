import java.io.File;
import java.util.ArrayList;
import java.util.List;

import controller.DeckReader;
import model.BoardCell;
import model.Card;

import model.PawnsGameModel;
import model.PlayerColor;
import view.PawnsTextualView;
import view.TextualView;

/**
 * Creates a fully played iteration of the game.
 */
public class PawnsBoard {
  /**
   * Creates a fully played out version of the game.
   *
   * @param args recieves arguments
   */
  public static void main(String[] args) {
    String path = "docs" + File.separator + "deck.config";
    File configFile = new File(path);

    // Read the deck from the configuration file.
    List<Card> deck = DeckReader.readDeck(configFile);


    PawnsGameModel model = new PawnsGameModel(3, 5);
    // Use the same deck for both players with a starting hand size of 5 and no shuffling.
    model.setupGame(new ArrayList<>(deck), new ArrayList<>(deck), 5, false);

    // Create the textual view.
    TextualView view = new PawnsTextualView(model);


    while (!model.isGameOver()) {
      System.out.println(view);

      boolean moveMade = false;
      List<Card> hand = model.getCurrentPlayerHand();
      BoardCell[][] board = model.getBoard();
      for (int r = 0; r < board.length && !moveMade; r++) {
        for (int c = 0; c < board[r].length && !moveMade; c++) {
          // For each card in hand, if a legal move is possible, place it.
          for (Card card : hand) {
            if (model.isLegalMove(r, c, card)) {
              model.placeCard(r, c, card);
              moveMade = true;
              break;
            }
          }
        }
      }

      // If no legal move was found, the player passes.
      if (!moveMade) {
        model.passTurn();
      }

      model.drawCard();
    }

    // Final board state.
    System.out.println(view);
    PlayerColor winner = model.determineWinner();
    if (winner == null) {
      System.out.println("Game Over! The game is a tie!");
    } else {
      System.out.println("Game Over! Winner: " + winner);
    }
  }
}



