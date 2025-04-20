package view;

import model.PawnsGameReadOnly;
import model.PlayerColor;

public class EnhancedPawnsFrame extends PawnsFrame {

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
