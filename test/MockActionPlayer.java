
import player.ActionPlayer;
import controller.ViewActions;
import model.PawnsGameReadOnly;

public class MockActionPlayer implements ActionPlayer {
  private final StringBuilder log;

  public MockActionPlayer(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void beginTurn(PawnsGameReadOnly model, ViewActions observer) {
    log.append("beginTurn\n");
  }
}
