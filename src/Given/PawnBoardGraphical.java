import controller.PawnsBoardController;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import model.Card;
import model.CardLoader;
import model.PawnsboardImpl;
import model.PlayerTeam;
import model.ReadonlyPawnsBoard;
import player.HumanPlayer;
import player.Player;
import player.computer.BoardControl;
import player.computer.ComputerPlayer;
import player.computer.Fill;
import player.computer.MaxRowScore;

/**
 * Main class for the Game of Pawns Board with a GUI.
 */
public class PawnBoardGraphical {

  /**
   * main method that runs the program.
   *
   * @param args arguments taken to the program. (path to the deck config file).
   */
  public static void main(String[] args) {
    if (args.length != 4) {
      throw new RuntimeException(
          "needs 4 args: red deck, blue deck, red player type, blu player type");
    }
    try {
      Path path;
      ArrayList<Card> leftDeck;
      ArrayList<Card> rightDeck;
      try {
        path = Paths.get(args[0], "");
        leftDeck = CardLoader.loadCards(args[0]);
        System.err.println("loaded cards from deck");
      } catch (InvalidPathException | ArrayIndexOutOfBoundsException e) {
        leftDeck = CardLoader.loadCards();
        System.err.println("unable to load cards for left deck or no deck supplied");
      }
      try {
        path = Paths.get(args[1], "");
        rightDeck = CardLoader.loadCards(args[1]);
        System.err.println("loaded cards from deck");
      } catch (InvalidPathException | ArrayIndexOutOfBoundsException e) {
        rightDeck = CardLoader.loadCards();
        System.err.println("unable to load cards for left deck or no deck supplied");
      }

      PawnsboardImpl model = new PawnsboardImpl(3, 5, 5);
      Player leftPlayer = pickPlayer(args[2], PlayerTeam.RED, model);
      Player rightPlayer = pickPlayer(args[3], PlayerTeam.BLUE, model);

      PawnsBoardController controllerLeft = new PawnsBoardController(model, leftPlayer);
      PawnsBoardController controllerRight = new PawnsBoardController(model, rightPlayer);
      model.startGame(leftDeck, rightDeck);
      controllerLeft.playGame();
      controllerRight.playGame();

      System.err.println("started game");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static Player pickPlayer(String s, PlayerTeam team, ReadonlyPawnsBoard model) {
    switch (s) {
      case "human":
        return new HumanPlayer(team, model);
      case "fill":
        return new ComputerPlayer(team, model, new Fill());
      case "control":
        return new ComputerPlayer(team, model, new BoardControl());
      case "rowScore":
        return new ComputerPlayer(team, model, new MaxRowScore());
      default:
        return new HumanPlayer(team, model);
    }
  }

}
