package provider.model;

import java.util.ArrayList;

/**
 * A read-only interface of the Pawnsboard game model.
 */
public interface ReadonlyPawnsBoard {

  Integer getColSize();

  Integer getRowSize();

  PlayerTeam getNextTurn();

  ArrayList<Integer> scoreRowsRightPlayer();

  ArrayList<Integer> scoreRowsLeftPlayer();

  ArrayList<Integer> scoreRows(PlayerTeam team);

  boolean isGameOver();

  PlayerTeam getWinner();

  Boolean isLegalMove(Move move, PlayerTeam team);

  ArrayList<PlayingCard> getLeftPlayerHand();

  ArrayList<PlayingCard> getRightPlayerHand();

  ArrayList<PlayingCard> getPlayerHand(PlayerTeam team);

  Object getBoardPiece(int row, int col);
}