package model;

import java.util.List;

import controller.ModelListener;

/**
 * Interface representing the model for the Pawn Board game.
 */
public interface PawnsGame extends PawnsGameReadOnly {

  /**
   * Initializes the game model with the specified board dimensions, decks, and initial hand size.
   * @param deckOne         the list of cards for player one ( Red)
   * @param deckTwo         the list of cards for player two ( Blue)
   * @param initialHandSize the number of cards to initially deal to each player;
   *                        cannot be greater than one third of the deck size
   * @param shuffle     decide whether the two decks should be shuffled or not
   */
  void setupGame(List<Card> deckOne, List<Card> deckTwo, int initialHandSize, boolean shuffle);


  /**
   * Places the specified card on the board at the given cell, if the move is legal.
   * Validate the move using  isLegalMove
   * Remove the appropriate pawns from the cell?
   * Place the card on the board and remove it from the current player's hand.
   * Apply the card's influence to the board (use private method)
   * Switch the turn to the opposing player.
   *
   * @param row  the row index where the card is to be placed
   * @param col  the column index where the card is to be placed
   * @param card the card to place
   * @throws IllegalArgumentException if the move is illegal
   */
  void placeCard(int row, int col, Card card);

  /**
   * Processes a pass by the current player. If two passes ends game.
   */
  void passTurn();

  /**
   * Draws a card for the current player if cards remain in their deck.
   * should update the player's hand.
   */
  void drawCard();

  void addModelListener(ModelListener listener);

  void startGame();

}

