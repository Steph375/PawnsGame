package view;

import java.awt.Color;


import model.PlayerColor;

/**
 * Object representing the color scheme to be used in visualizing the board in the PawnsBoard GUI.
 */
public interface ColorScheme {
  /**
   * gets the background color for the game board.
   * @return Color
   */
  Color getBackgroundColor();

  /**
   * Gets the board color background default dark grey.
   * @return color
   */
  Color getBoardBackground();

  /**
   * Gets the color that the player will be drawn as based on which player they are red or blue.
   * @param color the player it is.
   * @return color based on player.
   */
  Color getPlayerColor(PlayerColor color);

  /**
   * Gets the color of all lined and righting within the board.
   * @return color of writing and lines in board.
   */
  Color getLineColor();

  /**
   * gets the color that will be used to highlight cells on the board.
   * @return highlight color.
   */
  Color getHighlightColor();

  /**
   * the text color of all writing in board panel outside of the actual board like the score.
   * @return color of text
   */
  Color getTextColor();
}

