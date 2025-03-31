package view;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import controller.ViewActions;
import model.IPlayer;
import model.PawnsGameReadOnly;

/**
 * A window of the PawnsBoard GUI.
 */
public class PawnsFrame extends JFrame implements PawnsView {
  private final BoardPanel boardPanel;
  private final HandPanel handPanel;

  /**
   * Creates a window of the PawnsBoard GUI.
   * @param model the game to visualize.
   * @param player the Player using this view.
   */
  public PawnsFrame(PawnsGameReadOnly model, IPlayer player) {
    super();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    if (model == null || player == null) {
      throw new IllegalArgumentException("parameters cannot be null");
    }

    this.setTitle("Player: " + player.getColor());

    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

    // create board and hand panels
    this.boardPanel = new BoardPanel(model);
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
