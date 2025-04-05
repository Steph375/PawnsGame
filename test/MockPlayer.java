

import model.Card;
import model.IPlayer;
import model.InfluencePosition;
import model.PawnCard;
import model.PlayerColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A mock of the Player class from the model for testing.
 */
public class MockPlayer implements IPlayer {
  private final PlayerColor color;
  private final List<Card> hand;
  private final List<Card> deck;
  private final StringBuilder log;

  /**
   * Creates a mock Player class from the model.
   * @param color the color of this player.
   * @param log StringBuilder used to track method calls.
   */
  public MockPlayer(PlayerColor color, StringBuilder log) {
    this.color = color;
    this.log = log;
    this.hand = new ArrayList<>();
    this.deck = new ArrayList<>();

    HashMap<InfluencePosition, Boolean> influence = new HashMap<>();
    influence.put(new InfluencePosition(0, 1), true); // top
    influence.put(new InfluencePosition(1, 0), true); // right

    hand.add(new PawnCard("TestHandCard1", 1, 5, influence));
    hand.add(new PawnCard("TestHandCard2", 2, 3, new HashMap<>()));

    deck.add(new PawnCard("TestDeckCard1", 1, 4, new HashMap<>()));
    deck.add(new PawnCard("TestDeckCard2", 3, 7, new HashMap<>()));
  }


  @Override
  public List<Card> getHand() {
    log.append("getHand\n");
    return new ArrayList<>(hand);
  }

  @Override
  public void drawCard() {
    log.append("drawCard\n");
    if (!deck.isEmpty()) {
      hand.add(deck.remove(0));
    }
  }

  @Override
  public List<Card> getDeck() {
    log.append("getDeck\n");
    return new ArrayList<>(deck);
  }

  @Override
  public PlayerColor getColor() {
    log.append("getColor\n");
    return color;
  }

  @Override
  public void removeCard(Card card) {
    log.append("removeCard ").append("\n");
    hand.remove(card);
  }

  @Override
  public int getDeckSize() {
    log.append("getDeckSize\n");
    return deck.size();
  }
}
