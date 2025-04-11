package AdapterStuff;


import provider.model.PlayerTeam;
import provider.model.PlayingCard;

public class PawnsCardToProviderCard implements PlayingCard {
  model.Card card;
  PlayerTeam playerTeam;

  public PawnsCardToProviderCard(model.Card card, PlayerTeam playerTeam) {
    this.card = card;
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
