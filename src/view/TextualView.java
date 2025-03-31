package view;


/**
 * Represents the view of the PawnsBoard Game.
 */
public interface TextualView {
  /**
   * Returns a string representation of the current board state.
   * Each row is formatted with the red row-score on the left, the row's cells in the middle,
   * and the blue row-score on the right.
   *
   * @return the board state as a string.
   */
  String toString();
}
