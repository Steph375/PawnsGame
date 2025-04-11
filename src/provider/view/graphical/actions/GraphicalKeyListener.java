package provider.view.graphical.actions;

import provider.controller.ViewActions;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Class for handling Keyboard actions.
 */
public class GraphicalKeyListener extends KeyAdapter {

  private final ViewActions observer;

  /**
   * A constructor for the key listener.
   *
   * @param observer controller of the game.
   */
  public GraphicalKeyListener(ViewActions observer) {
    this.observer = observer;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      observer.handleSpacePress();
    }
  }
}
