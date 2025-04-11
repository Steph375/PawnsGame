package provider.model;

/**
 * reprisents pawn(s) on a board, and stores how many of them.
 */
public class Pawn {

  private Integer quantity;
  private PlayerTeam team;

  public Pawn(Pawn pawn) {
    this.quantity = pawn.quantity;
    this.team = pawn.team;
  }

  /**
   * constructor for pawn.
   *
   * @param quantity the quantity.
   */
  public Pawn(Integer quantity) {
    this.quantity = quantity;
  }

  /**
   * Constructor for pawn.
   *
   * @param quantity the quantity.
   * @param team     the team. can be null.
   */
  public Pawn(Integer quantity, PlayerTeam team) {
    this.quantity = quantity;
    this.team = team;
  }

  /**
   * getter for pawn quanitty.
   *
   * @return the quantity.
   */
  public Integer getQuantity() {
    return quantity;
  }

  /**
   * setteer for pawn quantity.
   *
   * @param quantity the quantity.
   */
  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  /**
   * getter for team. Can be null
   *
   * @return the team.
   */
  public PlayerTeam getTeam() {
    return team;
  }

  /**
   * setter for the team type.
   *
   * @param team the team.
   */
  public void setTeam(PlayerTeam team) {
    this.team = team;
  }


}
