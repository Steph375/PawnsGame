package provider.model;

/**
 * An enum of Player Teams, Red or Blue.
 */
public enum PlayerTeam {
  RED, BLUE;

  /**
   * Helper function that returns the opposite player team.
   *
   * @return opposite team.
   */
  public PlayerTeam getOpposite() {
    return this == RED ? BLUE : RED;
  }
}
