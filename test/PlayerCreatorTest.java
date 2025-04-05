

import controller.ViewActions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.Card;
import model.PawnCard;
import model.PawnsGameReadOnly;
import model.PlayerColor;
import player.ActionPlayer;
import player.PlayerCreator;
import strategies.Move;
import strategies.Strategies;

import java.util.HashMap;
import java.util.List;

/**
 * A class for testing the behavior of the PlayerCreator.
 */
public class PlayerCreatorTest {
  private StringBuilder log;
  private MockModel model;
  private MockObserver observer;
  private Card testCard;

  @Before
  public void setup() {
    log = new StringBuilder();
    model = new MockModel(log);
    observer = new MockObserver(log);

    // Card that strategies can use
    testCard = new PawnCard("TestCard", 1, 5, new HashMap<>());
    model.setCurrentPlayer(PlayerColor.RED);
    model.setCurrentPlayerHand(List.of(testCard));
  }

  @Test
  public void testBuildHumanPlayerDoesNothing() {
    ActionPlayer player = PlayerCreator.build(PlayerColor.RED, "human");
    player.beginTurn(model, observer);
    String[] lines = log.toString().split("\n");
    //every create sets red player first
    Assert.assertEquals("setCurrentPlayer RED", lines[0]);
    Assert.assertEquals("setCurrentPlayerHand 1 cards", lines[1]);

  }

  @Test
  public void testHumanPlayerBlueDoesNothing() {
    ActionPlayer player = PlayerCreator.build(PlayerColor.BLUE, "human");
    model.setCurrentPlayer(PlayerColor.BLUE);
    model.setCurrentPlayerHand(List.of(testCard));

    System.out.println(log);
    player.beginTurn(model, observer);
    String[] lines = log.toString().split("\n");
    //must set red player first no matter what
    Assert.assertEquals("setCurrentPlayer RED", lines[0]);
    Assert.assertEquals("setCurrentPlayerHand 1 cards", lines[1]);
    Assert.assertEquals("setCurrentPlayer BLUE", lines[2]);
    Assert.assertEquals("setCurrentPlayerHand 1 cards", lines[3]);

  }

  @Test
  public void testSingleStrategyMachinePlayerPlays() {
    ActionPlayer player = PlayerCreator.build(PlayerColor.RED, "strategy1");
    player.beginTurn(model, observer);

    String[] lines = log.toString().split("\n");

    Assert.assertEquals("setCurrentPlayer RED", lines[0]);
    Assert.assertEquals("setCurrentPlayerHand 1 cards", lines[1]);
    Assert.assertEquals("getCurrentPlayer", lines[2]);
    Assert.assertEquals("isLegalMove at 0,0 with card model.PawnCard@78bb", lines[7]);
    Assert.assertEquals("onCardSelected 0 RED", lines[9]);
    Assert.assertEquals("onCellSelected 0 0", lines[10]);
    Assert.assertEquals("onConfirmMove", lines[11]);
  }

  @Test
  public void testMultipleStrategiesMachinePlayerPlays() {
    ActionPlayer player = PlayerCreator.build(PlayerColor.RED, "strategy1,strategy2");
    player.beginTurn(model, observer);

    System.out.println(log);
    String[] lines = log.toString().split("\n");

    Assert.assertEquals("setCurrentPlayer RED", lines[0]);
    Assert.assertEquals("isLegalMove at 0,0 with card model.PawnCard@78bb", lines[7]);
    Assert.assertEquals("onCellSelected 0 0", lines[10]);
    Assert.assertEquals("onConfirmMove", lines[11]);
  }

  @Test
  public void testUnknownStrategyThrows() {
    IllegalArgumentException ex = Assert.assertThrows(IllegalArgumentException.class, () ->
            PlayerCreator.build(PlayerColor.RED, "strategyX"));
    Assert.assertEquals("Unknown strategy: strategyx", ex.getMessage());
  }

  @Test
  public void testNullTypeThrows() {
    IllegalArgumentException ex = Assert.assertThrows(IllegalArgumentException.class, () ->
            PlayerCreator.build(PlayerColor.RED, null));
    Assert.assertEquals("Player type cannot be null.", ex.getMessage());
  }

  @Test
  public void testEmptyStrategyThrows() {
    IllegalArgumentException ex =  Assert.assertThrows(IllegalArgumentException.class, () ->
            PlayerCreator.build(PlayerColor.RED, ""));
    Assert.assertEquals("At least one strategy must be provided.", ex.getMessage());
  }

  @Test
  public void testBluePlayerPlaysOnBlueTurn() {
    model.setCurrentPlayer(PlayerColor.BLUE);
    model.setCurrentPlayerHand(List.of(testCard)); // Must be BLUE's hand
    ActionPlayer player = PlayerCreator.build(PlayerColor.BLUE, "strategy1");

    player.beginTurn(model, observer);

    String[] lines = log.toString().split("\n");

    Assert.assertEquals("setCurrentPlayer BLUE", lines[2]);
    Assert.assertEquals("setCurrentPlayerHand 1 cards", lines[3]);
    Assert.assertEquals("getCurrentPlayer", lines[4]);
    Assert.assertEquals("getCurrentPlayerHand", lines[6]);
    Assert.assertEquals("onCardSelected 0 BLUE", lines[11]);
    Assert.assertEquals("onCellSelected 0 2", lines[12]);
    Assert.assertEquals("onConfirmMove", lines[13]);
  }

  @Test
  public void testBluePlayerDoesNotPlayOnRedTurn() {
    model.setCurrentPlayer(PlayerColor.RED);
    model.setCurrentPlayerHand(List.of(testCard)); // BLUE player's card irrelevant here
    ActionPlayer player = PlayerCreator.build(PlayerColor.BLUE, "strategy1");

    player.beginTurn(model, observer);

    System.out.println(log);
    String[] lines = log.toString().split("\n");

    Assert.assertEquals("setCurrentPlayer RED", lines[0]);
    Assert.assertEquals("setCurrentPlayerHand 1 cards", lines[1]);
    Assert.assertEquals("getCurrentPlayer", lines[4]);
    Assert.assertEquals("onPassTurn", lines[5]);
  }

  private static class MockObserver implements ViewActions {
    private final StringBuilder log;

    public MockObserver(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void onCardSelected(int index, PlayerColor color) {
      log.append("onCardSelected ").append(index).append(" ").append(color).append("\n");
    }

    @Override
    public void onCellSelected(int row, int col) {
      log.append("onCellSelected ").append(row).append(" ").append(col).append("\n");
    }

    @Override
    public void onConfirmMove() {
      log.append("onConfirmMove\n");
    }

    @Override
    public void onPassTurn() {
      log.append("onPassTurn\n");
    }
  }

  private static class MockStrategy implements Strategies {
    private final Card card;

    public MockStrategy(Card card) {
      this.card = card;
    }

    @Override
    public Move chooseMove(PawnsGameReadOnly model) {
      return new Move(card, 0, 0);
    }
  }

}
