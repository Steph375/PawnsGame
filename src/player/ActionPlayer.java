package player;

import controller.ViewActions;
import model.PawnsGameReadOnly;

public interface ActionPlayer {
  void beginTurn(PawnsGameReadOnly model, ViewActions observer);
}
