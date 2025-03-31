package model;

import java.util.List;

import controller.ViewActions;

/**
 * Represents a Player in the Pawns Game.
 */
public interface IPlayer {


  /**
   * gets the hand of the player.
   *
   * @return a copy of the hand
   */
  List<Card> getHand();

  /**
   * Draws a card from the players deck.
   */
  void drawCard();

  /**
   * Produces a copy of the players deck.
   * @return copy of the deck
   */
  List<Card> getDeck();


  /**
   * Produces the players color.
   * @return players color
   */
  PlayerColor getColor();


  /**
   * Removes given card from players hand.
   * @param card card to be removed
   */
  void removeCard(Card card);

  /**
   * gets the size of list of cards in the deck.
   * @return deck size
   */
  int getDeckSize();



}
