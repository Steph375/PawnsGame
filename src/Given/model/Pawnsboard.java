package model;

import java.util.ArrayList;

/**
 * Interface for the model of the game of PawnsBoard.
 */
public interface Pawnsboard extends ReadonlyPawnsBoard {

  void startGame(ArrayList<Card> leftPlayerDeck, ArrayList<Card> rightPlayerDeck);

  void makeMove(Move move);

}
