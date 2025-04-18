package view;

import java.awt.Color;

import model.PlayerColor;

public class DefaultColor implements ColorScheme {

  @Override
  public Color getBackgroundColor() {
    return Color.decode("#F3EDED");
  }

  @Override
  public Color getBoardBackground() {
    return Color.GRAY;
  }



  @Override
  public Color getPlayerColor(PlayerColor color) {
    return color == PlayerColor.RED
            ? Color.decode("#fca6ae")
            : Color.decode("#93bef3");
  }

  @Override
  public Color getLineColor() {
    return Color.BLACK;
  }



  @Override
  public Color getHighlightColor() {
    return Color.CYAN;
  }

  @Override
  public Color getTextColor() {
    return Color.BLACK;
  }
}

