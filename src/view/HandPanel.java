package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import controller.ViewActions;
import model.Card;
import model.InfluencePosition;
import model.PawnsGameReadOnly;
import model.PlayerColor;

/**
 * Creates a panel that visualizes and handles the operations of the part of the PawnsBoard GUI that
 * visualizes the given Player's hand.
 */
public class HandPanel extends JPanel implements IHandPanel {
  PawnsGameReadOnly model;
  private int selectedCardIndex;

  // holds card dimensions for mouse click
  private final List<Rectangle> cardBounds;
  private int cardWidth;
  private int handSize;
  private final PlayerColor player;

  /**
   * Creates a panel for visualizing the hand of the given player.
   *
   * @param model  the PawnsBoard game to visualize.
   * @param player the player that is using this instance of the GUI.
   */
  HandPanel(PawnsGameReadOnly model, PlayerColor player) {
    super();

    if (model == null || player == null) {
      throw new IllegalArgumentException("parameters cannot be null");
    }
    this.model = model;
    this.player = player;

    selectedCardIndex = -1; // this value indicates no card selected
    this.cardBounds = new ArrayList<>();
  }

  /**
   * Checks the number of cards in this player's hand to update handSize and cardWidth.
   */
  private void updateHand() {
    if (player == PlayerColor.RED) {
      handSize = this.model.getPlayerRed().getHand().size();
    } else {
      handSize = this.model.getPlayerBlue().getHand().size();
    }

    if (handSize == 0) {
      cardWidth = 0;
    } else {
      cardWidth = getWidth() / handSize;
    }
  }

  /**
   * Connects the controller.ViewActions observe to this panel.
   *
   * @param observer the controller.ViewActions object that handles user input from the GUI.
   */
  public void subscribe(ViewActions observer) {
    this.addMouseListener(new HandMouseListener(observer));
  }

  /**
   * Mouse listener to obtain and act on mouse clicks.
   */
  class HandMouseListener extends MouseAdapter {
    private final ViewActions observer;

    /**
     * Creates a MouseListener for this panel.
     *
     * @param observer the controller.ViewActions object that handles mouse output.
     */
    public HandMouseListener(ViewActions observer) {
      this.observer = observer;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      int clickX = e.getX();
      int clickY = e.getY();

      if (player == model.getCurrentPlayer()) {
        for (int index = 0; index < cardBounds.size(); index++) {
          Rectangle box = cardBounds.get(index);
          if (box.contains(clickX, clickY)) {
            if (selectedCardIndex == index) {
              selectedCardIndex = -1;
            } else {
              selectedCardIndex = index;
            }
            observer.onCardSelected(index, player);
            repaint();
            return;
          }
        }
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    int width = this.getWidth();
    int height = this.getHeight();

    if (this.player.equals(PlayerColor.RED)) {
      g2d.setColor(Color.decode("#fca6ae"));
    } else {
      g2d.setColor(Color.decode("#93bef3"));
    }

    // fill panel with either red or blue for card background color
    g2d.fillRect(0, 0, width, height);

    this.updateHand();

    if (selectedCardIndex != -1) {
      int x = selectedCardIndex * cardWidth;
      g2d.setColor(Color.CYAN);
      g2d.fillRect(x, 0, cardWidth, height);
    }

    this.drawHand(g2d);
  }

  /**
   * Draws the cards in this players hand.
   *
   * @param g2d graphics object for drawing.
   */
  private void drawHand(Graphics2D g2d) {
    cardBounds.clear(); // Clear previous frame’s boxes

    List<Card> hand;
    if (player == PlayerColor.RED) {
      hand = this.model.getPlayerRed().getHand();
    } else {
      hand = this.model.getPlayerBlue().getHand();
    }

    if (hand.isEmpty()) {
      return;
    }

    this.updateHand();
    for (int i = 0; i < handSize; i++) {
      int cardX = (i * cardWidth);
      int nextCardX = ((i + 1) * cardWidth);
      int width = nextCardX - cardX;
      Rectangle rect = new Rectangle(cardX, 0, width, getHeight());

      cardBounds.add(rect);
      drawCard(g2d, hand.get(i), rect, i); // pass card index
    }

  }

  /**
   * Draws an individual card in the hand.
   *
   * @param g2d    graphics object for drawing.
   * @param card   the card to be drawn.
   * @param bounds a rectangle object representing the size of the card.
   * @param index  the card's index in the hand.
   */
  protected void drawCard(Graphics2D g2d, Card card, Rectangle bounds, int index) {
    this.drawCardText(g2d, card, bounds, index);
    drawInfluenceGrid(g2d, card.getInfluence(), cardBounds.get(index));
  }

  /**
   * Draws the text of the card (everything except the influence grid).
   * @param g2d graphics object for drawing.
   * @param card the card to be drawn.
   * @param bounds a rectangle object representing the size of the card.
   * @param index the card's index in the hand.
   */
  protected void drawCardText(Graphics2D g2d, Card card, Rectangle bounds, int index) {
    int x = bounds.x;
    int y = bounds.y;
    int width = bounds.width;
    int height = bounds.height;

    g2d.setColor(Color.BLACK);
    g2d.drawRect(x, y, width, height);

    int fontSize = Math.max(13, Math.min((width / 9 * 4 / 5), height / 9));


    g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
    g2d.setColor(Color.BLACK);
    g2d.drawString(card.getName(), x + 5, y + fontSize);
    g2d.drawString("Cost: " + card.getCost(), x + 5, y + fontSize * 2);
    g2d.drawString("Value: " + card.getValueScore(), x + 5, y + fontSize * 3);
  }

  /**
   * Draws the grid that represents a card's influence for an individual card.
   *
   * @param g2d                graphics object for drawing.
   * @param influencePositions list of positions that the card has influence over.
   * @param cardRect           rectangle representing the size of the card.
   */
  private void drawInfluenceGrid(Graphics2D g2d, List<InfluencePosition>
          influencePositions, Rectangle cardRect) {
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

        boolean isInfluenced = false;
        for (InfluencePosition pos : influencePositions) {
          if (pos.getX() == col && pos.getY() == row) {
            isInfluenced = true;
            break;
          }
        }

        if (row == 0 && col == 0) {
          g2d.setColor(Color.YELLOW);
        } else if (isInfluenced) {
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
