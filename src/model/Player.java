package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Player in the Pawns Game model.
 * A player has control over their own deck and hand and score.
 * they are assigned a player color once when made.
 */
public class Player implements IPlayer {
  private final PlayerColor color;
  private List<Card> deck;
  private List<Card> hand;

  /**
   * Constructs a new Player with the given color, deck, and initial hand size.
   *
   * @param color           the player's color (RED or BLUE)
   * @param deck            the full deck of cards for the player (must not be null)
   * @param initialHandSize the number of cards to deal to the player's hand initially;
   *                        must be positive and no greater than one third of the deck size.
   * @throws IllegalArgumentException if the deck is null or if the initial hand size is invalid.
   */
  public Player(PlayerColor color, List<Card> deck, int initialHandSize) {
    if (color == null) {
      throw new IllegalArgumentException("color cannot be null");
    }
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null.");
    }
    if (initialHandSize <= 0 || initialHandSize > deck.size() / 3) {
      throw new IllegalArgumentException("Initial hand size must be positive and at most one " +
              "third of the deck size.");
    }
    this.color = color;
    // Create a copy of the deck so that the original list is not modified.
    this.deck = new ArrayList<>(deck);
    this.hand = new ArrayList<>();
    // Deal the initial hand.
    for (int i = 0; i < initialHandSize; i++) {
      this.hand.add(this.deck.remove(0));
    }
  }

  /**
   * Draws a card from the players deck.
   */
  public void drawCard() {
    if (!deck.isEmpty()) {
      hand.add(deck.remove(0));
    }
  }

  /**
   * gets the hand of the player.
   *
   * @return a copy of the hand
   */
  public List<Card> getHand() {
    return new ArrayList<>(this.hand);
  }

  /**
   * Produces a copy of the players deck.
   *
   * @return copy of the deck
   */
  public List<Card> getDeck() {
    return new ArrayList<>(this.deck);
  }

  /**
   * Produces the players color.
   *
   * @return players color
   */
  public PlayerColor getColor() {
    return this.color;
  }

  /**
   * Removes given card from players hand.
   *
   * @param card card to be removed
   */
  @Override
  public void removeCard(Card card) {
    this.hand.remove(card);
  }

  /**
   * gets the size of list of cards in the deck.
   *
   * @return deck size
   */
  public int getDeckSize() {
    return this.deck.size();
  }

}