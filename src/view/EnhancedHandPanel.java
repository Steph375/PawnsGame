package view;

import java.awt.*;
import java.util.List;

import model.Card;
import model.InfluencePosition;
import model.PawnsGameReadOnly;
import model.PlayerColor;

public class EnhancedHandPanel extends HandPanel {
  public EnhancedHandPanel(PawnsGameReadOnly model, PlayerColor player) {
    super(model, player);
  }

  @Override
  protected void drawCard(Graphics2D g2d, Card card, Rectangle bounds, int index) {
    if (card == null) {
      return; 
    }
    super.drawCardText(g2d, card, bounds, index);

    drawEnhancedInfluence(g2d, card.getInfluence(), card.getUpgrades(), card.getDevalues(),
         bounds);
  }

  private void drawEnhancedInfluence(Graphics2D g2d, List<InfluencePosition>
          influence, List<InfluencePosition> upgrades, List<InfluencePosition> devalues,
                                     Rectangle cardRect) {
    int x = cardRect.x;
    int y = cardRect.y;
    int width = cardRect.width;
    int height = cardRect.height;

    int topPadding = (int) (height * 0.42);
    int bottomPadding = (int) (height * 0.05);

    int availableHeight = height - topPadding - bottomPadding;
    int gridCellSize = availableHeight / 5;

    if (width < gridCellSize * 5) {
      gridCellSize = width / 5;
    }

    int gridWidth = gridCellSize * 5;


    int gridStartX = x + ((width - gridWidth) * 6 / 4) / 2;
    int gridStartY = y + topPadding;

    for (int row = -2; row <= 2; row++) {
      for (int col = -2; col <= 2; col++) {
        int correctedRow = -row;
        int cellX = gridStartX + (col + 2) * gridCellSize;
        int cellY = gridStartY + correctedRow * gridCellSize;

        InfluencePosition pos = new InfluencePosition(col, row);

        if (row == 0 && col == 0) {
          g2d.setColor(Color.YELLOW);
        } else if (upgrades.contains(pos)) {
          g2d.setColor(Color.GREEN);
        } else if (devalues.contains(pos)) {
          g2d.setColor(Color.MAGENTA);
        } else if (influence.contains(pos)) {
          g2d.setColor(Color.CYAN);
        } else {
          g2d.setColor(Color.DARK_GRAY);
        }

        g2d.fillRect(cellX, cellY, gridCellSize, gridCellSize);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(cellX, cellY, gridCellSize, gridCellSize);
      }
    }
  }

}
