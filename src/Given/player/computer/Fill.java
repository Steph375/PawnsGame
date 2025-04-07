package player.computer;

import java.util.ArrayList;
import model.Card;
import model.Move;
import model.MoveType;
import model.PlayerTeam;
import model.ReadonlyPawnsBoard;

/**
 * A fill class for the Computer player.
 */
public class Fill implements Strategy {

  /**
   * When called, this function will return a new move object, detailing the moves the player would
   * like to make. The model class (Pawnsboard) will be passed into the constructor and the player
   * can use this to gather information (for example for an ai player) to determine the next move.
   *
   * @param team team of the Computer player.
   */
  @Override
  public Move makeMove(PlayerTeam team, ReadonlyPawnsBoard board) {
    ArrayList<Card> hand = board.getPlayerHand(team);

    for (int i = 0; i < board.getRowSize(); i++) {
      for (int j = 0; j < board.getColSize(); j++) {
        for (int k = 0; k < hand.size(); k++) {
          Move potentialMove = new Move(MoveType.PLACE, i, j, k);
          if (board.isLegalMove(potentialMove, team)) {
            return potentialMove;
          }

        }

      }
    }

    return new Move(MoveType.PASS);
  }
}
