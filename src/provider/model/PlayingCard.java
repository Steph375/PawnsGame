package provider.model;


/**
 * Provider didn't write java doc here.
 */
public interface PlayingCard {

  /**
   * getter for the owner of this card.
   *
   * @return the owner.
   */
  PlayerTeam getOwnerTeam();

  /**
   * setter for the owner of this card.
   *
   * @param ownerTeam the owner.
   */
  void setOwnerTeam(PlayerTeam ownerTeam);
}
