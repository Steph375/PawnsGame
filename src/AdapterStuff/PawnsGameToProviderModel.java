package AdapterStuff;

import java.util.ArrayList;
import java.util.List;

import model.Card;
import model.PawnsGame;
import model.PlayerColor;
import provider.model.Move;
import provider.model.Pawn;
import provider.model.PlayerTeam;
import provider.model.ReadonlyPawnsBoard;

public class PawnsGameToProviderModel implements ReadonlyPawnsBoard {
  private final PawnsGame model;

  public PawnsGameToProviderModel(PawnsGame model) {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    this.model = model;
  }

  @Override
  public Integer getColSize() {
    return this.model.getWidth();
  }

  @Override
  public Integer getRowSize() {
    return this.model.getHeight();
  }

  @Override
  public PlayerTeam getNextTurn() {
    return this.model.getCurrentPlayer() == PlayerColor.RED ? PlayerTeam.RED : PlayerTeam.BLUE;
  }

  @Override
  public ArrayList<Integer> scoreRowsRightPlayer() {
    ArrayList<Integer> scores = new ArrayList<>();
    for (int row = 0; row < model.getHeight(); row++) {
      scores.add(model.getRowScores(row)[1]);
    }
    return scores;
  }

  @Override
  public ArrayList<Integer> scoreRowsLeftPlayer() {
    ArrayList<Integer> scores = new ArrayList<>();
    for (int row = 0; row < model.getHeight(); row++) {
      scores.add(model.getRowScores(row)[0]);
    }
    return scores;
  }

  @Override
  public ArrayList<Integer> scoreRows(PlayerTeam team) {
    return team == PlayerTeam.RED ? scoreRowsLeftPlayer() : scoreRowsRightPlayer();
  }

  @Override
  public boolean isGameOver() {
    return model.isGameOver();
  }

  @Override
  public PlayerTeam getWinner() {
    PlayerColor winner = model.determineWinner();
    if (winner == null) return null;
    return winner == PlayerColor.RED ? PlayerTeam.RED : PlayerTeam.BLUE;
  }

  @Override
  public Boolean isLegalMove(Move move, PlayerTeam team) {
    List<Card> hand = model.getCurrentPlayerHand();
    if (move.moveType == provider.model.MoveType.PASS) return true;
    if (move.handIdx >= hand.size()) return false;
    return model.isLegalMove(move.placeRow, move.placeCol, hand.get(move.handIdx));
  }

  @Override
  public ArrayList<provider.model.PlayingCard> getLeftPlayerHand() {
    ArrayList<provider.model.PlayingCard> result = new ArrayList<>();
    for (Card c : model.getPlayerRed().getHand()) {
      result.add(new PawnsCardToProviderCard(c, PlayerTeam.RED));
    }
    return result;
  }

  @Override
  public ArrayList<provider.model.PlayingCard> getRightPlayerHand() {
    ArrayList<provider.model.PlayingCard> result = new ArrayList<>();
    for (Card c : model.getPlayerBlue().getHand()) {
      result.add(new PawnsCardToProviderCard(c, PlayerTeam.BLUE));
    }
    return result;
  }

  @Override
  public ArrayList<provider.model.PlayingCard> getPlayerHand(PlayerTeam team) {
    return team == PlayerTeam.RED ? getLeftPlayerHand() : getRightPlayerHand();
  }

  @Override
  public Object getBoardPiece(int row, int col) {
    model.BoardCell cell = model.getBoard()[row][col];
    if (cell.getCard() != null) {
      PlayerTeam team = cell.getColor() == PlayerColor.RED ? PlayerTeam.RED : PlayerTeam.BLUE;
      return new PawnsCardToProviderCard(cell.getCard(), team);
    }
    if (cell.getPawns() > 0) {
      return new Pawn(cell.getPawns(), cell.getColor() == PlayerColor.RED ? PlayerTeam.RED : PlayerTeam.BLUE);
    }
    return null;
  }
}
