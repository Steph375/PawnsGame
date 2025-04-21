package view;

import model.PawnsGameReadOnly;
import model.PlayerColor;

/**
 * Frame for the game that works with the enhanced boardPanel and handPanel.
 */
public class EnhancedPawnsFrame extends PawnsFrame {

  /**
   * Constructor sets values same as pawns frame and the switches out the board
   * and hand panel for the advanced versions.
   *
   * @param model  takes an enhanced model
   * @param player which color player's frame it is
   * @param scheme the color scheme of the board
   */
  public EnhancedPawnsFrame(PawnsGameReadOnly model, PlayerColor player, ColorScheme scheme) {
    super(model, player, scheme);
    this.remove(this.boardPanel);
    this.remove(this.handPanel);

    this.boardPanel = new EnhancedBoardPanel(model, scheme);
    this.handPanel = new EnhancedHandPanel(model, player);

    this.add(this.boardPanel);
    this.add(this.handPanel);

    this.revalidate();
    this.repaint();
  }
}
