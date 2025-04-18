
import model.Card;
import model.PawnsGame;
import model.PawnsGameModel;
import model.PlayerColor;
import player.ActionPlayer;
import controller.DeckReader;
import controller.GameController;
import controller.PawnsController;
import player.PlayerCreator;
import view.DefaultColor;
import view.HighContrast;
import view.PawnsFrame;
import view.PawnsView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Runnable class to play the PawnsBoard game with human or AI players.
 */
public final class PawnsBoardGame {
  /**
   * Main method to instantiate the model, controllers, and views for the specified players.
   * @param args Command line input. Should be in the form [redDeckPath] [blueDeckPath]
   *             [redPlayerType] [bluePlayerType].
   */
  public static void main(String[] args) {
    if (args.length != 4) {
      System.out.println("Please input: <redDeckPath> <blueDeckPath> <redType> <blueType>");
      System.exit(1);
    }

    String redDeckPath = args[0];
    String blueDeckPath = args[1];
    String redType = args[2];
    String blueType = args[3];

    List<Card> redDeck = DeckReader.readDeck(new File(redDeckPath));
    List<Card> blueDeck = DeckReader.readDeck(new File(blueDeckPath));

    PawnsGame model = new PawnsGameModel(5, 7);
    model.setupGame(new ArrayList<>(redDeck), new ArrayList<>(blueDeck), 5, false);

    ActionPlayer redAPlayer = PlayerCreator.build(PlayerColor.RED, "human");
    ActionPlayer blueAPlayer = PlayerCreator.build(PlayerColor.BLUE, "human");

    PawnsView redView = new PawnsFrame(model, PlayerColor.RED, new HighContrast());
    PawnsView blueView = new PawnsFrame(model, PlayerColor.BLUE, new HighContrast());

    PawnsController redController = new GameController(model, PlayerColor.RED, redAPlayer, redView);
    PawnsController blueController = new GameController(model, PlayerColor.BLUE, blueAPlayer,
            blueView);

    redController.playGame(model);
    blueController.playGame(model);

    model.startGame();
  }
}
