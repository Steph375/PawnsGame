

import controller.ViewActions;
import model.*;
import org.junit.Before;
import org.junit.Test;

import player.MachinePlayer;
import strategies.Move;
import strategies.Strategies;


import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class MachinePlayerTest {
  private StringBuilder log;
  private MockModel model;
  private MockStrategy strategy;
  private ViewActions observer;
  private Card validCard;

  @Before
  public void setup() {
    log = new StringBuilder();
    model = new MockModel(log);
    strategy = new MockStrategy(log);
    observer = new MockObserver(log);
    validCard = new PawnCard("CardA", 1, 5, new HashMap<>());
  }

  @Test
  public void testPassWhenNotYourTurn() {
    model.setCurrentPlayer(PlayerColor.BLUE); // Machine is RED
    MachinePlayer machine = new MachinePlayer(PlayerColor.RED, strategy);
    machine.beginTurn(model, observer);

    String[] lines = log.toString().split("\n");
    assertEquals("setCurrentPlayer BLUE", lines[0]);
    assertEquals("getCurrentPlayer", lines[1]);
    assertEquals("onPassTurn", lines[2]);
  }

  @Test
  public void testPassWhenMoveIsNull() {
    model.setCurrentPlayer(PlayerColor.RED);
    strategy.setMove(null);
    MachinePlayer machine = new MachinePlayer(PlayerColor.RED, strategy);
    machine.beginTurn(model, observer);

    String[] lines = log.toString().split("\n");
    assertEquals("setCurrentPlayer RED", lines[0]);
    assertEquals("getCurrentPlayer", lines[1]);
    assertEquals("chooseMove", lines[2]);
    assertEquals("onPassTurn", lines[3]);
  }

  @Test
  public void testPassWhenCardNotInHand() {
    model.setCurrentPlayer(PlayerColor.RED);
    model.setCurrentPlayerHand(List.of()); // hand is empty
    strategy.setMove(new Move(validCard, 0, 0));

    MachinePlayer machine = new MachinePlayer(PlayerColor.RED, strategy);
    machine.beginTurn(model, observer);

    String[] lines = log.toString().split("\n");
    assertEquals("setCurrentPlayer RED", lines[0]);
    assertEquals("setCurrentPlayerHand 0 cards", lines[1]);
    assertEquals("getCurrentPlayer", lines[2]);
    assertEquals("chooseMove", lines[3]);
    assertEquals("getCurrentPlayerHand", lines[4]);
    assertEquals("onPassTurn", lines[5]);
  }

  @Test
  public void testValidMove() {
    model.setCurrentPlayer(PlayerColor.RED);
    model.setCurrentPlayerHand(List.of(validCard));
    strategy.setMove(new Move(validCard, 1, 2));

    MachinePlayer machine = new MachinePlayer(PlayerColor.RED, strategy);
    machine.beginTurn(model, observer);

    String[] lines = log.toString().split("\n");
    assertEquals("setCurrentPlayer RED", lines[0]);
    assertEquals("setCurrentPlayerHand 1 cards", lines[1]);
    assertEquals("getCurrentPlayer", lines[2]);
    assertEquals("chooseMove", lines[3]);
    assertEquals("getCurrentPlayerHand", lines[4]);
    assertEquals("onCardSelected 0 RED", lines[5]);
    assertEquals("onCellSelected 1 2", lines[6]);
    assertEquals("onConfirmMove", lines[7]);
  }

  //Mock Classes

  private static class MockStrategy implements Strategies {
    private final StringBuilder log;
    private Move move;

    public MockStrategy(StringBuilder log) {
      this.log = log;
    }

    public void setMove(Move move) {
      this.move = move;
    }

    @Override
    public Move chooseMove(PawnsGameReadOnly model) {
      log.append("chooseMove\n");
      return move;
    }
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
}
