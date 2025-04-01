package controller;

import model.PawnsGameReadOnly;

public interface ActionPlayer{
  void beginTurn(PawnsGameReadOnly model, ViewActions observer);
}
