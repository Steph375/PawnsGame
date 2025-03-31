import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Card;
import model.InfluencePosition;
import model.PawnCard;
import model.Player;
import model.PlayerColor;

/**
 * A class to test the behavior of the Player class.
 */
public class PlayerTest {
  private List<Card> deck;
  private PawnCard card1;

  @Before
  public void setUp() {
    deck = new ArrayList<>();
    // Create 9 PawnCards with an empty influence map.
    card1 = new PawnCard("Card1", 1, 2, new HashMap<InfluencePosition, Boolean>());
    PawnCard card2 = new PawnCard("Card2", 1, 2, new HashMap<InfluencePosition, Boolean>());
    PawnCard card3 = new PawnCard("Card3", 1, 2, new HashMap<InfluencePosition, Boolean>());
    PawnCard card4 = new PawnCard("Card4", 1, 2, new HashMap<InfluencePosition, Boolean>());
    PawnCard card5 = new PawnCard("Card5", 1, 2, new HashMap<InfluencePosition, Boolean>());
    PawnCard card6 = new PawnCard("Card6", 1, 2, new HashMap<InfluencePosition, Boolean>());
    PawnCard card7 = new PawnCard("Card7", 1, 2, new HashMap<InfluencePosition, Boolean>());
    PawnCard card8 = new PawnCard("Card8", 1, 2, new HashMap<InfluencePosition, Boolean>());
    PawnCard card9 = new PawnCard("Card9", 1, 2, new HashMap<InfluencePosition, Boolean>());

    deck.add(card1);
    deck.add(card2);
    deck.add(card3);
    deck.add(card4);
    deck.add(card5);
    deck.add(card6);
    deck.add(card7);
    deck.add(card8);
    deck.add(card9);
  }

  @Test
  public void testConstructorValid() {
    // With 9 cards, maximum initial hand size is 9/3 = 3.
    Player player = new Player(PlayerColor.RED, deck, 3);
    Assert.assertEquals(3, player.getHand().size());
  }

  @Test
  public void testConstructorNullDeck() {
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new Player(PlayerColor.RED, null, 2);
    });
  }

  @Test
  public void testConstructorInvalidHandSize() {
    // Test hand size less than or equal to 0.
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new Player(PlayerColor.RED, deck, 0);
    });
    // Test hand size greater than one third so 4 should fail
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new Player(PlayerColor.RED, deck, 4);
    });
  }

  @Test
  public void testDrawCard() {
    Player player = new Player(PlayerColor.RED, new ArrayList<>(deck), 3);
    int initialHandSize = player.getHand().size();
    player.drawCard();
    Assert.assertEquals(initialHandSize + 1, player.getHand().size());
  }

  @Test
  public void testDrawCardDeckDecreases() {
    // This test assumes that the Player class has a public method getDeckSize() that returns the
    // number of cards remaining in the deck.
    Player player = new Player(PlayerColor.RED, new ArrayList<>(deck), 3);
    int initialHandSize = player.getHand().size();
    int initialDeckSize = player.getDeck().size();

    // card that should be drawn
    Card expectedDrawnCard = player.getDeck().get(0);

    player.drawCard();

    //hand size should increase by one, deck size should decrease by one.
    Assert.assertEquals(initialHandSize + 1, player.getHand().size());
    Assert.assertEquals(initialDeckSize - 1, player.getDeck().size());

    // verify that the drawn card is the expected one.
    Assert.assertTrue(player.getHand().contains(expectedDrawnCard));
  }

  @Test
  public void testGetHand() {
    Player player = new Player(PlayerColor.RED, new ArrayList<>(deck), 3);
    List<Card> hand = player.getHand();
    Assert.assertNotNull(hand);
    Assert.assertEquals(3, hand.size());
  }

  @Test
  public void testRemoveCardFromHand() {
    Player player = new Player(PlayerColor.RED, new ArrayList<>(deck), 3);
    List<Card> initialHand = player.getHand();

    player.removeCard(card1);
    Assert.assertNotEquals(initialHand, player.getHand());

    initialHand.remove(card1);
    Assert.assertEquals(initialHand, player.getHand());
  }
}