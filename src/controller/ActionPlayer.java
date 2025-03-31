package controller;


import model.PawnsGame;

public interface ActionPlayer{
  void beginTurn(PawnsGame model, ViewActions observer);
}
