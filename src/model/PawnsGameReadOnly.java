package model;

import java.util.List;

/**
 * Represents the model for the PawnsBoardGame only with observation methods.
 */
public interface PawnsGameReadOnly {
  /**
   * Returns the color of the player whose turn it currently is.
   *
   * @return the current player's color
   */
  PlayerColor getCurrentPlayer();

  /**
   * Calculates the total score for the specified player.
   *
   * @param color the player's color whose total score is to be calculated
   * @return the total score
   */
  int calculateTotalScore(PlayerColor color);

  /**
   * Determines the winner of the game based on the current total scores.
   *
   * @return the model.PlayerColor representing the winner,
   * or null if the game is tied
   */
  PlayerColor determineWinner();

  /**
   * Returns the current player's hand.
   *
   * @return a list of cards in the current player's hand
   * @throws IllegalStateException if the game has not started.
   */
  List<Card> getCurrentPlayerHand();

  /**
   * Gets current board.
   *
   * @return a copy of the current board.
   * @throws IllegalStateException if the game has not started.
   */
  BoardCell[][] getBoard();

  /**
   * Sees if the game is over or not.
   *
   * @return a boolean of whether the game has ended or not
   */
  boolean isGameOver();

  /**
   * Checks if the given move is legal.
   * That its the correct players turn and the player has enough pawns to cover
   * the cost of the space
   * and that the space isnt already occupied.
   *
   * @param row  the row the card will be placed in
   * @param col  the column the card will be placed in
   * @param card the card the is going to be placed
   * @return boolean indicate whether the move is possible or not
   */
  boolean isLegalMove(int row, int col, Card card);

  /**
   * Gets the redplayer in the game.
   *
   * @return IPlayer red player
   */
  IPlayer getPlayerRed();

  /**
   * Gets the blue player in the game.
   *
   * @return Iplayer blue player
   */
  IPlayer getPlayerBlue();

  /**
   * Gets the width of the board.
   *
   * @return number of columns.
   */
  int getWidth();

  /**
   * Gets the height of the board.
   *
   * @return number of rows.
   */
  int getHeight();

  /**
   * Checks how many times the players passes in a row
   *
   * @return int value of the number of passes
   */
  int getPasses();

  /**
   * returns the row scores for red and blue in the given row.
   *
   * @param row the row index on the board.
   * @return an array where index 0 is red's score and index 1 is blue's score.
   */
  int[] getRowScores(int row);
}
