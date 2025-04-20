package view;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import controller.ViewActions;
import model.PawnsGameReadOnly;
import model.PlayerColor;

/**
 * A window of the PawnsBoard GUI.
 */
public class PawnsFrame extends JFrame implements PawnsView {
  protected BoardPanel boardPanel;
  protected HandPanel handPanel;
  private final ColorScheme scheme;

  /**
   * Creates a window of the PawnsBoard GUI.
   * @param model the game to visualize.
   * @param player the Player using this view.
   */
  public PawnsFrame(PawnsGameReadOnly model, PlayerColor player, ColorScheme scheme) {
    super();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    if (model == null || player == null) {
      throw new IllegalArgumentException("parameters cannot be null");
    }

    this.setTitle("Player: " + player);

    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    this.scheme = scheme;
    // create board and hand panels
    this.boardPanel = new BoardPanel(model, scheme);
    this.handPanel = new HandPanel(model, player);

    // set the size of components
    Dimension frameSize = new Dimension(1200, 900);
    Dimension boardSize = new Dimension(1200, 600);
    Dimension handSize = new Dimension(1200, 300);

    setPreferredSize(frameSize);

    boardPanel.setPreferredSize(boardSize);
    boardPanel.setMaximumSize(boardSize);
    boardPanel.setMinimumSize(new Dimension(200, 200));

    handPanel.setPreferredSize(handSize);
    handPanel.setMaximumSize(handSize);
    handPanel.setMinimumSize(new Dimension(200, 200));

    // add panels to frame
    add(boardPanel);
    add(handPanel);

    pack(); // Size window to fit preferred sizes
    setLocationRelativeTo(null); // Center on screen

    setVisible(true);
  }



  @Override
  public void refresh() {
    this.revalidate();
    this.repaint();
  }


  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void subscribe(ViewActions observer) {
    this.boardPanel.subscribe(observer);
    this.handPanel.subscribe(observer);

    this.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(KeyEvent e) {
        // do nothing, use key pressed
      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          observer.onConfirmMove();
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
          observer.onPassTurn();
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // do nothing, use key pressed
      }
    });
  }

  @Override
  public void setTitle(String title) {
    super.setTitle(title);
  }
}
