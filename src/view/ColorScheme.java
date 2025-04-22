package view;

import java.awt.Color;


import model.PlayerColor;

/**
 *
 */
public interface ColorScheme {
  /**
   *
   * @return
   */
  Color getBackgroundColor();

  /**
   *
   * @return
   */
  Color getBoardBackground();

  /**
   *
   * @param color
   * @return
   */
  Color getPlayerColor(PlayerColor color);

  /**
   * 
   * @return
   */
  Color getLineColor();

  Color getHighlightColor();

  Color getTextColor();
}

