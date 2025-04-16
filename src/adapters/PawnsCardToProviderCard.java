package adapters;


import provider.model.PlayerTeam;
import provider.model.PlayingCard;

/**
 * Object adapter to adapt our PawnsCard to the provider's PlayingCard.
 */
public class PawnsCardToProviderCard implements PlayingCard {
  model.Card card;
  PlayerTeam playerTeam;

  /**
   * Creates a new object adapter to adapt the given PawnsCard to the provider's PlayingCard.
   * @param card the PawnsCard to adapt.
   * @param playerTeam the player to whom the card belongs.
   */
  public PawnsCardToProviderCard(model.Card card, PlayerTeam playerTeam) {
    if (card == null || playerTeam == null) {
      throw new IllegalArgumentException("parameters cannot be null");
    }
    this.card = card;
    this.playerTeam = playerTeam;
  }

  @Override
  public PlayerTeam getOwnerTeam() {
    return this.playerTeam;
  }

  @Override
  public void setOwnerTeam(PlayerTeam ownerTeam) {
    this.playerTeam = ownerTeam;
  }
}
