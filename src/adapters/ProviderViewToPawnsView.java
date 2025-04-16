package adapters;

import controller.ViewActions;
import model.PlayerColor;
import provider.model.PlayerTeam;
import provider.view.graphical.GUIView;
import view.PawnsView;


/**
 * Object adapter to convert the provider's GUI to ours.
 */
public class ProviderViewToPawnsView implements PawnsView {
  private final GUIView providerView;

  /**
   * Constructs an adapter around the provider's GUIView to match our PawnsView interface.
   */
  public ProviderViewToPawnsView(GUIView view, PlayerColor playerColor) {
    if (view == null || playerColor == null) {
      throw new IllegalArgumentException("model and playerColor cannot be null");
    }
    this.providerView = view;
  }

  /**
   * Subscribe our controller as the ViewActions listener.
   */
  @Override
  public void subscribe(ViewActions observer) {
    provider.controller.ViewActions adapter = new ActionsAdapter(observer);
    this.providerView.setActionsListener(adapter);
  }

  @Override
  public void setTitle(String title) {
    this.providerView.setTitle(title);
  }

  @Override
  public void makeVisible() {
    this.providerView.makeVisible();
  }

  @Override
  public void refresh() {
    this.providerView.update();
  }



  private PlayerTeam convertColor(PlayerColor color) {
    return color == PlayerColor.RED ? PlayerTeam.RED : PlayerTeam.BLUE;
  }
}
