

import controller.EnhancedDeckReader;
import controller.GameController;
import controller.PawnsController;
import model.Card;
import model.EnhancedPawnsGame;
import model.PawnsGame;
import model.PlayerColor;
import player.ActionPlayer;
import player.PlayerCreator;
import view.DefaultColor;
import view.EnhancedPawnsFrame;
import view.HighContrast;
import view.PawnsView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Runnable class to play the enhanced PawnsBoard game with upgraded/devalued influence.
 */
public final class PawnsBoardGame {
  /**
   * Main method to instantiate the enhanced model, controllers,
   * and views for the specified players.
   *
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

    // Always use the enhanced deck reader
    List<Card> redDeck = EnhancedDeckReader.readDeck(new File(redDeckPath));
    List<Card> blueDeck = EnhancedDeckReader.readDeck(new File(blueDeckPath));

    // Always use the enhanced model
    PawnsGame model = new EnhancedPawnsGame(5, 7);
    model.setupGame(new ArrayList<>(redDeck), new ArrayList<>(blueDeck), 5, true);

    // Build human or AI players
    ActionPlayer redAPlayer = PlayerCreator.build(PlayerColor.RED, redType);
    ActionPlayer blueAPlayer = PlayerCreator.build(PlayerColor.BLUE, blueType);

    // Use enhanced views and normal color scheme
    PawnsView redView = new EnhancedPawnsFrame(model, PlayerColor.RED, new DefaultColor());
    PawnsView blueView = new EnhancedPawnsFrame(model, PlayerColor.BLUE, new HighContrast());

    // Connect controllers
    PawnsController redController = new GameController(model, PlayerColor.RED, redAPlayer, redView);
    PawnsController blueController = new GameController(model,
            PlayerColor.BLUE, blueAPlayer, blueView);

    redController.playGame(model);
    blueController.playGame(model);

    model.startGame();
  }
}
