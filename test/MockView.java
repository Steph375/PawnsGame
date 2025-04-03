
import controller.ViewActions;
import view.PawnsView;

public class MockView implements PawnsView {
  private final StringBuilder log;

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
