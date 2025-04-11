package AdapterStuff;


import controller.ViewActions;


public class ActionsAdapter implements provider.controller.ViewActions {
  private final ViewActions ourViewActions;


  public ActionsAdapter(ViewActions ourViewActions) {
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
