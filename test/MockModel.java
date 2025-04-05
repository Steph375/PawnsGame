import java.util.ArrayList;
import java.util.List;

import controller.ModelListener;
import model.BoardCell;
import model.Card;
import model.IPlayer;
import model.PawnsGame;
import model.PlayerColor;

/**
 * A mock of the model for testing purposes.
 */
public class MockModel implements PawnsGame {
  private final StringBuilder log;
  private final IPlayer red = new MockPlayer(PlayerColor.RED, new StringBuilder());
  private final IPlayer blue = new MockPlayer(PlayerColor.BLUE, new StringBuilder());

  private PlayerColor currentPlayer = PlayerColor.RED;
  private List<Card> currentPlayerHand = new ArrayList<>();

  /**
   * Creates a mock model with the given StringBuilder to track method calls.
   * @param log the StringBuilder used to log method calls.
   */
  public MockModel(StringBuilder log) {
    this.log = log;
  }


  /**
   * A helper for testing purposes to set the current player in this mock model.
   * @param color the color of the player to set the current player to.
   */
  public void setCurrentPlayer(PlayerColor color) {
    this.currentPlayer = color;
    log.append("setCurrentPlayer ").append(color).append("\n");
  }

  /**
   * A helper for testing purposed to set the hand of the current player.
   * @param hand the list of Cards to set the current player's hand to.
   */
  public void setCurrentPlayerHand(List<Card> hand) {
    this.currentPlayerHand = new ArrayList<>(hand);
    log.append("setCurrentPlayerHand ").append(hand.size()).append(" cards\n");
  }


  @Override
  public void setupGame(List<Card> deckOne, List<Card> deckTwo, int initialHandSize, boolean shuffle) {
    log.append(String.format("setupGame with handSize=%d shuffle=%b\n", initialHandSize, shuffle));
  }

  @Override
  public void placeCard(int row, int col, Card card) {
    log.append(String.format("placeCard at %d,%d with card %s\n", row, col, card.toString()));
  }

  @Override
  public void passTurn() {
    log.append("passTurn\n");
  }

  @Override
  public void drawCard() {
    log.append("drawCard\n");
  }

  @Override
  public void addModelListener(ModelListener listener) {
    log.append("addModelListener\n");
  }

  @Override
  public void startGame() {
    log.append("startGame\n");
  }


  @Override
  public PlayerColor getCurrentPlayer() {
    log.append("getCurrentPlayer\n");
    return currentPlayer;
  }

  @Override
  public List<Card> getCurrentPlayerHand() {
    log.append("getCurrentPlayerHand\n");
    return new ArrayList<>(currentPlayerHand);
  }

  @Override
  public int calculateTotalScore(PlayerColor color) {
    log.append("calculateTotalScore " + color + "\n");
    return (color == PlayerColor.RED) ? 10 : 20;
  }

  @Override
  public PlayerColor determineWinner() {
    log.append("determineWinner\n");
    return PlayerColor.BLUE;
  }

  @Override
  public BoardCell[][] getBoard() {
    log.append("getBoard\n");
    return new BoardCell[3][3];
  }

  @Override
  public boolean isGameOver() {
    log.append("isGameOver\n");
    return false;
  }

  @Override
  public boolean isLegalMove(int row, int col, Card card) {
    log.append(String.format("isLegalMove at %d,%d with card %s\n", row, col, card.toString()));
    return true;
  }

  @Override
  public IPlayer getPlayerRed() {
    log.append("getPlayerRed\n");
    return red;
  }

  @Override
  public IPlayer getPlayerBlue() {
    log.append("getPlayerBlue\n");
    return blue;
  }

  @Override
  public int getWidth() {
    log.append("getWidth\n");
    return 3;
  }

  @Override
  public int getHeight() {
    log.append("getHeight\n");
    return 3;
  }

  @Override
  public int getPasses() {
    log.append("getPasses\n");
    return 0;
  }

  @Override
  public int[] getRowScores(int row) {
    log.append("getRowScores " + row + "\n");
    return new int[]{5, 7};
  }
}