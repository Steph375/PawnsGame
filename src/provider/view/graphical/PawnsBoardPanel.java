package provider.view.graphical;

import provider.controller.ViewActions;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;
import provider.model.Card;
import provider.model.Pawn;
import provider.model.PlayerTeam;
import provider.model.ReadonlyPawnsBoard;
import provider.view.graphical.actions.GraphicalMouseListener;

/**
 * Panel class for drawing the game board.
 */
public class PawnsBoardPanel extends JPanel {

  private final ReadonlyPawnsBoard model;
  private final int cellSize = 140;
  private final int gridSize = 10;
  private int selectedR = -1;
  private int selectedC = -1;


  /**
   * Constructor for the PawnsBoardPanel.
   *
   * @param model model to be visualized.
   */
  public PawnsBoardPanel(ReadonlyPawnsBoard model) {
    this.model = model;
    setPreferredSize(new Dimension(
        cellSize * model.getRowSize(),
        cellSize * model.getColSize()));
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(600, 400);
  }

  /**
   * Method for highlighting selected cells.
   *
   * @param row row of the cell.
   * @param col column of the cell.
   */
  public void setSelectedCell(int row, int col) {
    this.selectedR = row;
    this.selectedC = col;
    repaint();
  }

  /**
   * Tell the panel that the player has unselected the cell (probably due to making a move).
   */
  public void clearSelection() {
    this.selectedR = -1;
    this.selectedC = -1;
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int rows = model.getRowSize();
    int cols = model.getColSize();
    int width = getWidth();
    int height = getHeight();

    int cellW = width / cols;
    int cellH = height / rows;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        int x = j * cellH;
        int y = i * cellW;

        g.setColor(Color.GRAY);
        g.fillRect(x, y, cellW, cellH);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(x, y, cellW, cellH);

        if (i == selectedR && j == selectedC) {
          g.setColor(Color.ORANGE);
          g.drawRect(x + 2, y + 2, cellW - 4, cellH - 4);
        }

        Object boardPiece = model.getBoardPiece(i, j);
        if (boardPiece instanceof Card) {
          drawCardGrid(g, ((Card) boardPiece).influence, x + cellW / 2, y + cellH / 2, cellW,
              cellH);
        }
        if (boardPiece instanceof Pawn) {
          Pawn p = (Pawn) boardPiece;
          g.setColor(p.getTeam() == PlayerTeam.RED ? Color.PINK : Color.BLUE);
          g.fillOval(x + 25, y + 25, 30, 30);

          g.setColor(Color.BLACK);
          String pawnQty = p.getQuantity().toString();
          FontMetrics fm = g.getFontMetrics(); // for centering the text
          int textX = x + 25 + (30 - fm.stringWidth(pawnQty)) / 2;
          int textY = y + 25 + ((30 - fm.getHeight()) / 2) + fm.getAscent();
          g.drawString(pawnQty, textX, textY);

        }
      }
    }
  }

  private void drawCardGrid(Graphics g, Integer[][] influence, int cardCenterX, int cardCenterY,
      int cellW, int cellH) {
    int subCellW = cellW / 5;
    int subCellH = cellH / 5;

    int topLeftX = cardCenterX - (subCellW * 2);
    int topLeftY = cardCenterY - (subCellH * 2);
    for (int i = 0; i < influence.length; i++) {
      for (int j = 0; j < influence[i].length; j++) {
        int influenceValue = influence[i][j];
        if (influenceValue == 0) {
          continue;
        }

        if (influenceValue == 2) {
          g.setColor(Color.ORANGE); //center of the card
        } else if (influenceValue == 1) {
          g.setColor(Color.CYAN); //influence
        }
        int x = topLeftX + influenceValue * subCellW;
        int y = topLeftY + influenceValue * subCellH;
        g.fillRect(x, y, subCellW, subCellH);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, subCellW, subCellH);
      }
    }
  }

  public void setMouseListener(ViewActions observer) {
    this.addMouseListener(new GraphicalMouseListener(observer));
  }


}
