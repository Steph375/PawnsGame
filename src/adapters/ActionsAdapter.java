package adapters;


import controller.ViewActions;

/**
 * Object adapter to adapt our ViewActions type to the provider's ViewActions.
 */
public class ActionsAdapter implements provider.controller.ViewActions {
  private final ViewActions ourViewActions;

  /**
   * Creates a new object adapter for our ViewActions to the provider's ViewActions.
   * @param ourViewActions our ViewActions object to be adapted.
   */
  public ActionsAdapter(ViewActions ourViewActions) {
    if (ourViewActions == null) {
      throw new IllegalArgumentException("ViewActions object cannot be null");
    }
    this.ourViewActions = ourViewActions;
  }

  @Override
  public void handleCellClick(int row, int col) {
    ourViewActions.onCellSelected(row, col);
  }

  @Override
  public void handleSpacePress() {
    ourViewActions.onPassTurn();
  }

  @Override
  public void gameOver() {

  }

  @Override
  public void setSelectedCard(int id) {

  }

}
