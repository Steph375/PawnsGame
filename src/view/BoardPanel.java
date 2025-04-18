package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import controller.ViewActions;
import model.BoardCell;
import model.Card;
import model.PawnsGameReadOnly;
import model.PlayerColor;

/**
 * A panel that handles that visualizes the part of the PawnsBoard GUI that represents the
 * game board.
 */
public class BoardPanel extends JPanel implements IBoardPanel {
  PawnsGameReadOnly model;
  private int selectedRow;
  private int selectedCol;
  private int cellSize;
  private int boardStartX;
  private int boardStartY;
  private ColorScheme scheme;


  /**
   * Creates a BoardPanel for the PawnsBoard GUI.
   * @param model the game to be visualized.
   */
  BoardPanel(PawnsGameReadOnly model, ColorScheme scheme) {
    super();
    this.model = model;
    this.selectedRow = -1;
    this.selectedCol = -1;
    this.scheme = scheme;
  }

  /**
   * Calculates and updates cellSize, boardStartX, and boardStartY fields.
   */
  private void updateBoardMetrics() {
    this.cellSize = Math.min((getWidth() * 4 / 5) / model.getWidth(),
            getHeight() / model.getHeight());
    this.boardStartX = (getWidth() - (model.getWidth() * cellSize)) / 2;
    this.boardStartY = (getHeight() - (model.getHeight() * cellSize)) / 2;
  }

  /**
   * Connects the controller.ViewActions observe to this panel.
   * @param observer the controller.ViewActions object that handles user input from the GUI.
   */
  public void subscribe(ViewActions observer) {
    this.addMouseListener(new BoardMouseListener(observer, model.getHeight(), model.getWidth()));
  }

  /**
   * Mouse listener to obtain and act on mouse clicks.
   */
  class BoardMouseListener extends MouseAdapter {
    private final ViewActions observer;
    private final int rows;
    private final int cols;

    /**
     * Creates a MouseListener for this panel.
     * @param observer the controller.ViewActions object that handles mouse output.
     * @param rows the number of rows on the board in this game.
     * @param cols the number of columns on the board in this game.
     */
    public BoardMouseListener(ViewActions observer, int rows, int cols) {
      this.observer = observer;
      this.rows = rows;
      this.cols = cols;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      updateBoardMetrics();

      //  click position relative to the board
      int col = (e.getX() - boardStartX) / cellSize;
      int row = (e.getY() - boardStartY) / cellSize;

      // Ensure clicks are within board bounds
      if (row >= 0 && row < rows && col >= 0 && col < cols) {
        if (selectedRow == row && selectedCol == col) {
          selectedRow = -1;
          selectedCol = -1;
        } else {
          selectedRow = row;
          selectedCol = col;
        }
        observer.onCellSelected(row, col);
        repaint();
      }
    }
  }

  /**
   * Paint component.
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    int width = this.getWidth();
    int height = this.getHeight();

    // draw light gray background
    g2d.setColor(scheme.getBackgroundColor());

    g2d.fillRect(0, 0, width, height);

    this.drawBoard(g2d);
  }

  /**
   * Draw the board the game is played on.
   * @param g2d the graphics object for drawing.
   */
  private void drawBoard(Graphics2D g2d) {
    int rows = model.getHeight();
    int cols = model.getWidth();
    this.updateBoardMetrics();
    // Draw darker gray board background
    g2d.setColor(scheme.getBoardBackground());
    g2d.fillRect(boardStartX, boardStartY, cols * cellSize, rows * cellSize);

    // Draw highlight if a cell is selected
    if (selectedRow != -1 && selectedCol != -1) {
      int xHighlight = boardStartX + (selectedCol * cellSize);
      int yHighlight = boardStartY + (selectedRow * cellSize);

      g2d.setColor(scheme.getHighlightColor());
      g2d.fillRect(xHighlight, yHighlight, cellSize, cellSize);
    }

    // Draw cells
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        int x = boardStartX + (col * cellSize);
        int y = boardStartY + (row * cellSize);

        boolean highlighted = selectedRow == row && selectedCol == col;

        this.drawCell(g2d, row, col, x, y, cellSize, cellSize, highlighted);
      }

      // Draw row scores on left and right of the board
      this.drawScores(g2d, row, boardStartX / 2,
              boardStartX + (cols * cellSize) + (boardStartX / 2),
              boardStartY + (row * cellSize) + (cellSize / 2));
    }
  }

  /**
   * Draw the row scores on either side of the given row.
   * @param g2d the graphics object for drawing.
   * @param row the row whose scores are being drawn.
   * @param redX the x position to draw the red player's score.
   * @param blueX the x position to draw the blue player's score.
   * @param y the y position to draw the scores.
   */
  private void drawScores(Graphics2D g2d, int row, int redX, int blueX, int y) {
    int redScore = model.getRowScores(row)[0];
    int blueScore = model.getRowScores(row)[1];

    g2d.setFont(new Font("Arial", Font.BOLD, 25));
    g2d.setColor(scheme.getTextColor());

    g2d.drawString(String.valueOf(redScore), redX, y);
    g2d.drawString(String.valueOf(blueScore), blueX, y);
  }

  /**
   * Draw an individual cell on the board.
   * @param g2d the graphics object for drawing.
   * @param row the row that this cell is positioned at.
   * @param col the column that this cell is positioned at.
   * @param x the x position to draw this cell.
   * @param y the y position to draw this cell.
   * @param cellWidth the width of the cell.
   * @param cellHeight the height of the cell.
   * @param highlighted whether this cell is selected.
   */
  private void drawCell(Graphics2D g2d, int row, int col, int x, int y,
                        int cellWidth, int cellHeight, boolean highlighted) {
    g2d.setColor(scheme.getLineColor());
    g2d.drawRect(x, y, cellWidth, cellHeight);

    BoardCell[][] board = model.getBoard();
    BoardCell cell = board[row][col];


    if (cell.getCard() != null) {
      this.drawCard(g2d, cell, x, y, cellWidth, cellHeight, highlighted);
    } else {
      this.drawPawns(g2d, cell, x, y, cellWidth, cellHeight);
    }
  }

  /**
   * Draw the pawns on a given cell.
   * @param g2d the graphics object for drawing.
   * @param cell the cell from the model whose pawns are being drawn.
   * @param x the x position of the cell.
   * @param y the y position of the cell.
   * @param cellWidth the width of the cell that the pawns are being drawn in.
   * @param cellHeight the height of the cell that the pawns are being drawn in.
   */
  private void drawPawns(Graphics2D g2d, BoardCell cell, int x, int y,
                         int cellWidth, int cellHeight) {
    int radius = cellWidth / 10;

    switch (cell.getPawns()) {
      case 1:
        this.drawSinglePawn(g2d, cell, x + cellWidth / 2, y + cellHeight / 2, radius);
        break;
      case 2:
        drawSinglePawn(g2d, cell, x + cellWidth / 3, y + cellHeight / 2, radius);
        drawSinglePawn(g2d, cell, x + 2 * cellWidth / 3, y + cellHeight / 2, radius);
        break;
      case 3:
        drawSinglePawn(g2d, cell, x + cellWidth / 3, y + cellHeight / 3, radius);
        drawSinglePawn(g2d, cell, x + 2 * cellWidth / 3, y + cellHeight / 3, radius);
        drawSinglePawn(g2d, cell, x + cellWidth / 2, y + 2 * cellHeight / 3, radius);
        break;
      // 0 pawns
      default:
        break;
    }
  }

  /**
   * Draw one pawn in a cell.
   * @param g2d the graphics object for drawing.
   * @param cell the cell from the model's board whose pawn is being drawn.
   * @param x the x position to draw the pawn at.
   * @param y the y position to draw the pawn at.
   * @param radius the radius of the circle that represents the pawn.
   */
  private void drawSinglePawn(Graphics2D g2d, BoardCell cell, int x, int y, int radius) {
    PlayerColor pawnColor = cell.getColor();


    if (pawnColor == PlayerColor.RED) {
      g2d.setColor(scheme.getPlayerColor(PlayerColor.RED));

    } else {
      g2d.setColor(scheme.getPlayerColor(PlayerColor.BLUE));
    }

    g2d.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    g2d.setColor(scheme.getTextColor());
    g2d.drawOval(x - radius, y - radius, radius * 2, radius * 2);
  }

  /**
   * Draw a card in a cell on the board.
   * @param g2d the graphics object for drawing.
   * @param cell the cell from the model's board whose card is being drawn.
   * @param x the x position of the cell.
   * @param y the y position of the cell.
   * @param cellWidth the width of the cell.
   * @param cellHeight the height of the cell.
   * @param highlighted whether this cell is selected.
   */
  private void drawCard(Graphics2D g2d, BoardCell cell, int x, int y,
                        int cellWidth, int cellHeight, boolean highlighted) {
    Card card = cell.getCard();
    PlayerColor ownerColor = cell.getColor();

    Color cellColor = ownerColor.equals(PlayerColor.RED)
            ? scheme.getPlayerColor(PlayerColor.RED)
            : scheme.getPlayerColor(PlayerColor.BLUE);

    if (highlighted) {
      cellColor = scheme.getHighlightColor();
    }

    // Fill the cell with player's color
    g2d.setColor(cellColor);
    g2d.fillRect(x, y, cellWidth, cellHeight);

    // Draw cell border
    g2d.setColor(scheme.getLineColor());
    g2d.drawRect(x, y, cellWidth, cellHeight);

    // Draw card value
    g2d.setColor(scheme.getTextColor());
    g2d.setFont(new Font("Arial", Font.BOLD, 20));

    // Center the text inside the cell
    FontMetrics fm = g2d.getFontMetrics();
    int textWidth = fm.stringWidth(card.getValueScore() + "");
    int textHeight = fm.getAscent();

    g2d.drawString(card.getValueScore() + "", x + (cellWidth - textWidth) / 2,
            y + (cellHeight + textHeight) / 2);
  }


}
