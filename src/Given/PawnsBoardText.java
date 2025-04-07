import java.util.ArrayList;
import java.util.List;
import model.Card;
import model.CardLoader;
import model.Move;
import model.MoveType;
import model.Pawnsboard;
import model.PawnsboardImpl;
import view.console.PawnsBoardTextualView;

/**
 * Main class for the game.
 */
public class PawnsBoardText {

  /**
   * main method of the game.
   *
   * @param args arguments taken from the user.
   */
  public static void main(String[] args) {
    ArrayList<Card> cards = CardLoader.loadCards();
    Pawnsboard pawnsboard = new PawnsboardImpl(3, 5, 5);
    pawnsboard.startGame(new ArrayList<>(List.copyOf(cards)), new ArrayList<>(List.copyOf(cards)));
    ArrayList<Move> moves = new ArrayList<>();
    moves.add(new Move(MoveType.PLACE, 0, 0, 0));
    moves.add(new Move(MoveType.PLACE, 0, 4, 0));
    moves.add(new Move(MoveType.PLACE, 1, 0, 1));
    moves.add(new Move(MoveType.PLACE, 1, 4, 1));
    moves.add(new Move(MoveType.PLACE, 2, 0, 2));
    moves.add(new Move(MoveType.PLACE, 2, 4, 2));
    moves.add(new Move(MoveType.PLACE, 1, 1, 3));
    moves.add(new Move(MoveType.PLACE, 0, 3, 3));
    moves.add(new Move(MoveType.PLACE, 2, 1, 3));
    moves.add(new Move(MoveType.PLACE, 2, 3, 3));
    moves.add(new Move(MoveType.PLACE, 1, 3, 0));
    moves.add(new Move(MoveType.PLACE, 0, 2, 0));
    moves.add(new Move(MoveType.PASS));
    moves.add(new Move(MoveType.PLACE, 0, 1, 3));

    moves.add(new Move(MoveType.PASS));
    moves.add(new Move(MoveType.PASS));
    PawnsBoardTextualView view = new PawnsBoardTextualView(pawnsboard);
    while (!pawnsboard.isGameOver()) {
      pawnsboard.makeMove(moves.get(0));
      moves.remove(0);
      view.printBoard();
    }
  }
}
