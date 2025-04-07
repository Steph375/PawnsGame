package view.graphical;

import controller.ViewActions;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import model.Card;
import model.PlayerTeam;
import view.graphical.actions.GraphicalMouseListener;

/**
 * A panel for the Card in the GUIView of the PawnsBoard game.
 */
public class CardPanel extends JPanel {

  private final int idx;
  private final Card card;
  private final PlayerTeam team;
  private boolean isSelected;

  /**
   * Constructor for the CardPanel.
   *
   * @param card Card to be displayed.
   * @param idx  id of the Card.
   */
  public CardPanel(Card card, int idx, PlayerTeam team) {
    this.idx = idx;
    this.card = card;
    isSelected = false;
    this.team = team;
    setPreferredSize(new Dimension(100, 160));
    setBackground(Color.PINK);
  }

  public int getIdx() {
    return this.idx;
  }

  /**
   * Called when this card panel has changed its selction state and thus should be repainted.
   *
   * @param selected if it is selected.
   */
  public void setSelected(boolean selected) {

    this.isSelected = selected;
    setBorder(BorderFactory.createLineBorder(
        selected ? Color.ORANGE : Color.BLACK,
        selected ? 6 : 1));
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (team == PlayerTeam.RED) {
      setBackground(new Color(255, 204, 204)); //light red color for red player.
    } else if (team == PlayerTeam.BLUE) {
      setBackground(new Color(204, 229, 255)); //light blue color for blue player.
    }

    if (isSelected) {
      setBorder(BorderFactory.createLineBorder(Color.ORANGE, 6));
    } else {
      setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }
    g.setFont(new Font("Calibri", Font.BOLD, 12));
    g.drawString(card.name, 5, 15);
    g.drawString("Cost: " + card.cost, 5, 30);
    g.drawString("Value: " + card.value, 5, 45);

    int cellSize = 12;
    int startX = 10;
    int startY = 60;

    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        Integer[][] grid = card.influence;
        if (team == PlayerTeam.BLUE) {
          grid = mirrorGrid(grid);
        }
        Integer val = grid[r][c];
        if (val == 1) {
          g.setColor(Color.CYAN);
        } else if (val == 2) {
          g.setColor(Color.ORANGE);
        } else {
          g.setColor(Color.DARK_GRAY);
        }

        int x = startX + c * cellSize;
        int y = startY + r * cellSize;
        g.fillRect(x, y, cellSize, cellSize);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, cellSize, cellSize); //border
      }
    }
  }

  private Integer[][] mirrorGrid(Integer[][] original) {
    int rows = original.length;
    int cols = original[0].length;
    Integer[][] mirrored = new Integer[rows][cols];

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        mirrored[i][j] = original[i][cols - j - 1];
      }
    }
    return mirrored;
  }

  public void setMouseListener(ViewActions observer) {
    this.addMouseListener(new GraphicalMouseListener(observer));
  }
}
