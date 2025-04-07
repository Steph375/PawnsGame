package player.computer;

import java.util.ArrayList;
import model.Move;
import model.MoveType;
import model.PawnsboardImpl;
import model.PlayerTeam;
import model.ReadonlyPawnsBoard;

/**
 * A class representing a strategy that gets a maximum score for the row.
 */
public class MaxRowScore implements Strategy {

  /**
   * When called, this function will return a new move object, detailing the moves the player would
   * like to make. The model class (Pawnsboard) will be passed into the constructor and the player
   * can use this to gather information (for example for an ai player) to determine the next move.
   *
   * @param team  Player team of the computer.
   * @param board current board of the Pawnsboard game.
   */
  @Override
  public Move makeMove(PlayerTeam team, ReadonlyPawnsBoard board) {
    ArrayList<Integer> playerScore = board.scoreRows(team);
    ArrayList<Integer> enemyScore = board.scoreRows(team.getOpposite());
    for (int i = 0; i < board.getRowSize(); i++) {

      if (playerScore.get(i) <= enemyScore.get(i)) { // only check
        for (int j = 0; j < board.getColSize(); j++) {
          for (int k = 0; k < board.getPlayerHand(team).size(); k++) {

            // i: columns
            // j: rows
            // k: hand idx
            Move potentialMove = new Move(MoveType.PLACE, i, j, k);
            if (board.isLegalMove(potentialMove, team) && board instanceof PawnsboardImpl) {
              PawnsboardImpl scratchBoard = new PawnsboardImpl((PawnsboardImpl) board);
              scratchBoard.makeMove(potentialMove);
              if (scratchBoard.scoreRows(team).get(i) > scratchBoard.scoreRows(team.getOpposite()).
                  get(i)) {
                return potentialMove;
              }
            }
          }
        }


      }
    }
    return new Move(MoveType.PASS);
  }
}
