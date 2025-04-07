
import view.PawnsView;
import controller.ViewActions;

/**
 * A mock of the GUI for testing purposes.
 */
public class MockView implements PawnsView {
  private final StringBuilder log;

  /**
   * Creates a mock GUI that tracks method calls with the given StringBuilder.
   * @param log the StringBuilder used to track method calls.
   */
  public MockView(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void refresh() {
    log.append("refresh\n");
  }

  @Override
  public void makeVisible() {
    log.append("makeVisible\n");
  }

  @Override
  public void subscribe(ViewActions observer) {
    log.append("subscribe\n");
  }

  @Override
  public void setTitle(String title) {
    log.append("setTitle ").append(title).append("\n");
  }
}
