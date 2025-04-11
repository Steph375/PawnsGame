package provider.model;

import java.util.ArrayList;

/**
 * Interface for the model of the game of PawnsBoard.
 */
public interface Pawnsboard extends ReadonlyPawnsBoard {

  void startGame(ArrayList<PlayingCard> leftPlayerDeck, ArrayList<PlayingCard> rightPlayerDeck);

  void makeMove(Move move);

}
