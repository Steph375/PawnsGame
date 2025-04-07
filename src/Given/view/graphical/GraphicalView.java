package view.graphical;

import view.graphical.actions.HumanViewActions;

public interface GraphicalView {
  void refresh(HumanViewActions viewActions);

  /**
   * Make the view Visible to start the game function.
   */
  void makeVisible();
}
