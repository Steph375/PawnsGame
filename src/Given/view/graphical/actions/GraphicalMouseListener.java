package view.graphical.actions;

import controller.ViewActions;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class for handling mouse actions.
 */
public class GraphicalMouseListener extends MouseAdapter {

  private final ViewActions observer;

  public GraphicalMouseListener(ViewActions observer) {
    this.observer = observer;
  }

  /**
   * Method for handling events on the action of MouseClicked.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    int size = e.getComponent().getWidth() / 5;
    int row = e.getY() / size;
    int col = e.getX() / size;
    this.observer.handleCellClick(row, col);
  }
}
