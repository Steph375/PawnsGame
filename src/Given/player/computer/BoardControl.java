package player.computer;

import model.Card;
import model.Move;
import model.MoveType;
import model.Pawn;
import model.Pawnsboard;
import model.PawnsboardImpl;
import model.PlayerTeam;
import model.ReadonlyPawnsBoard;

/**
 * A BoardControl class that tells the computer on how to interact with the board.
 */
public class BoardControl implements Strategy {

  /**
   * When called, this function will return a new move object, detailing the moves the player would
   * like to make. The model class (Pawnsboard) will be passed into the constructor and the player
   * can use this to gather information (for example for an ai player) to determine the next move.
   *
   * @param team  Player team of the computer.
   * @param board current board of the game.
   */
  @Override
  public Move makeMove(PlayerTeam team, ReadonlyPawnsBoard board) {
    Move bestMoveSoFar = null;
    Integer bestMoveScore = 0;
    for (int i = 0; i < board.getRowSize(); i++) {
      for (int j = 0; j < board.getColSize(); j++) {
        for (int k = 0; k < board.getPlayerHand(team).size(); k++) {

          Move potenialMove = new Move(MoveType.PLACE, i, j, k);
          if (board.isLegalMove(potenialMove, team)) {
            PawnsboardImpl pb = new PawnsboardImpl((Pawnsboard) board);
            pb.makeMove(potenialMove);
            if (countOwnedSpaces(team, pb) > bestMoveScore) {
              bestMoveScore = countOwnedSpaces(team, pb);
              bestMoveSoFar = potenialMove;
            }
          }

        }
      }
    }
    if (bestMoveSoFar == null) {
      return new Move(MoveType.PASS);
    }
    return bestMoveSoFar;
  }

  private Integer countOwnedSpaces(PlayerTeam team, ReadonlyPawnsBoard board) {
    int ownedSpaces = 0;
    for (int i = 0; i < board.getRowSize(); i++) {
      for (int j = 0; j < board.getColSize(); j++) {
        Object boardPiece = board.getBoardPiece(i, j);
        if ((boardPiece instanceof Pawn && ((Pawn) boardPiece).getTeam() == team) || (
            boardPiece instanceof Card && ((Card) boardPiece).getOwnerTeam() == team)) {
          ownedSpaces += 1;
        }

      }
    }
    return ownedSpaces;
  }

}
