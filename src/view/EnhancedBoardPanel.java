package view;

import java.awt.*;

import model.BoardCell;
import model.PawnsGameReadOnly;

/**
 * Draws the enhanced board with the upgrades and devalues from the enhanced model.
 */
public class EnhancedBoardPanel extends BoardPanel {
  public EnhancedBoardPanel(PawnsGameReadOnly model, ColorScheme scheme) {
    super(model, scheme);
  }

  @Override
  protected void drawCell(Graphics2D g2d, int row, int col, int x, int y,
                          int cellWidth, int cellHeight, boolean highlighted) {
    // Call the original cell drawing logic (pawns or card)
    super.drawCell(g2d, row, col, x, y, cellWidth, cellHeight, highlighted);

    // Then draw the modifier (upgrade/devalue score)
    BoardCell cell = model.getBoard()[row][col];
    int adjustment = cell.getUpgrade() - cell.getDevalue();
    if (adjustment != 0) {
      String mod = (adjustment > 0 ? "+" : "") + adjustment;
      g2d.setFont(new Font("Arial", Font.BOLD, 14));
      g2d.setColor(this.scheme.getLineColor());

      FontMetrics fm = g2d.getFontMetrics();
      int textWidth = fm.stringWidth(mod);
      int modX = x + (cellWidth - textWidth) / 2;
      int modY = y + cellHeight - 4; // draw near bottom of cell

      g2d.drawString(mod, modX, modY);
    }
  }

}
