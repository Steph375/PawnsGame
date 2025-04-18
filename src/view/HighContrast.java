package view;

import java.awt.Color;

import model.PlayerColor;

public class HighContrast implements ColorScheme {

  @Override
  public Color getBackgroundColor() {
    return  Color.decode("#F3EDED");
  }

  @Override
  public Color getBoardBackground() {
    return Color.BLACK;
  }


  @Override
  public Color getPlayerColor(PlayerColor color) {
    return color == PlayerColor.RED ? Color.RED : Color.CYAN;
  }

  @Override
  public Color getLineColor() {
    return Color.WHITE;
  }



  @Override
  public Color getHighlightColor() {
    return Color.YELLOW;
  }


  @Override
  public Color getTextColor() {
    return Color.BLACK;
  }
}
