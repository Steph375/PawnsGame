package view;

import java.awt.Color;


import model.PlayerColor;


public interface ColorScheme {
  Color getBackgroundColor();
  Color getBoardBackground();
  Color getPlayerColor(PlayerColor color);
  Color getLineColor();
  Color getHighlightColor();
  Color getTextColor();
}

