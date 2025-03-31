import java.util.List;

import model.Card;
import model.PlayerColor;
import model.IPlayer;


class DummyPlayer implements IPlayer {
  private final PlayerColor color;
  private final List<Card> hand;
  private final List<Card> deck = List.of(); // Empty deck for the mock

  DummyPlayer(PlayerColor color, List<Card> hand) {
    this.color = color;
    this.hand = hand;
  }

  @Override
  public List<Card> getHand() {
    return hand;
  }

  @Override
  public void drawCard() {
    throw new UnsupportedOperationException("drawCard not supported in DummyPlayer");
  }

  @Override
  public List<Card> getDeck() {
    return deck;
  }

  @Override
  public PlayerColor getColor() {
    return color;
  }

  @Override
  public void removeCard(Card card) {
    throw new UnsupportedOperationException("removeCard not supported in DummyPlayer");
  }

  @Override
  public int getDeckSize() {
    return 0;
  }
}
