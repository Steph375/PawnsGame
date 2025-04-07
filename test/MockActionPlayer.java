
import player.ActionPlayer;
import model.PawnsGameReadOnly;
import controller.ViewActions;

/**
 * A mock ActionPlayer for testing purposes.
 */
public class MockActionPlayer implements ActionPlayer {
  private final StringBuilder log;

  /**
   * Creates a mock ActionPlayer with the given StringBuilder to track method calls.
   * @param log the StringBuilder used to track method calls.
   */
  public MockActionPlayer(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void beginTurn(PawnsGameReadOnly model, ViewActions observer) {
    log.append("beginTurn\n");
  }
}
