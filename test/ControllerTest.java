
import controller.GameController;
import model.PawnsGame;
import model.PlayerColor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * A class for testing the behavior of the GUI controller, the GameController class.
 */
public class ControllerTest {
  private StringBuilder log;
  private PawnsGame model;
  private GameController controller;

  @Before
  public void setUp() {
    log = new StringBuilder();
    model = new MockModel(log);
    MockView view = new MockView(log);
    MockActionPlayer autoPlayer = new MockActionPlayer(log);
    controller = new GameController(model, PlayerColor.RED, autoPlayer, view);
  }

  @Test
  public void testPlayGame() {
    controller.playGame(model);
    String[] lines = log.toString().split("\n");

    Assert.assertEquals("getPlayerRed", lines[0]);
    Assert.assertEquals("addModelListener", lines[1]);
    Assert.assertEquals("subscribe", lines[2]);
    Assert.assertEquals("makeVisible", lines[3]);

  }

  @Test
  public void testOnCardSelectedWrongTurn() {
    controller.onCardSelected(0, PlayerColor.RED);
    String[] lines = log.toString().split("\n");
    Assert.assertEquals("getPlayerRed", lines[0]);
  }

  @Test
  public void testOnCardAndCellSelectedWhenIsTurn() {
    controller.onTurnChanged(PlayerColor.RED);
    controller.onCardSelected(0, PlayerColor.RED);
    controller.onCellSelected(1, 2);
    String[] lines = log.toString().split("\n");

    Assert.assertEquals("getPlayerRed", lines[0]);
    Assert.assertEquals("setTitle Player: RED (Your Turn)", lines[1]);
    Assert.assertEquals("refresh", lines[2]);
  }

  @Test
  public void testOnConfirmMoveWithoutSelection() {
    controller.onTurnChanged(PlayerColor.RED);
    controller.onConfirmMove();

    String[] lines = log.toString().split("\n");

    Assert.assertEquals("getPlayerRed", lines[0]);
    Assert.assertEquals("setTitle Player: RED (Your Turn)", lines[1]);
    Assert.assertEquals("refresh", lines[2]);
    Assert.assertEquals("beginTurn", lines[3]);

  }

  @Test
  public void testOnConfirmMoveValid() {
    controller.onTurnChanged(PlayerColor.RED);
    controller.onCardSelected(0, PlayerColor.RED);
    controller.onCellSelected(0, 0);
    controller.onConfirmMove();

    String[] lines = log.toString().split("\n");

    Assert.assertEquals("getPlayerRed", lines[0]);
    Assert.assertEquals("setTitle Player: RED (Your Turn)", lines[1]);
    Assert.assertEquals("refresh", lines[2]);
    Assert.assertEquals("beginTurn", lines[3]);
    Assert.assertEquals("placeCard at 0,0 with card model.PawnCard@86f7", lines[4]);

  }

  @Test
  public void testOnPassTurn() {
    controller.onTurnChanged(PlayerColor.RED);
    controller.onPassTurn();
    String[] lines = log.toString().split("\n");

    Assert.assertEquals("getPlayerRed", lines[0]);
    Assert.assertEquals("setTitle Player: RED (Your Turn)", lines[1]);
    Assert.assertEquals("refresh", lines[2]);
    Assert.assertEquals("beginTurn", lines[3]);
    Assert.assertEquals("passTurn", lines[4]);
    Assert.assertEquals("refresh", lines[5]);
  }

  @Test
  public void testOnTurnChangedHandlesYourTurnAndOpponentTurn() {
    controller.onTurnChanged(PlayerColor.BLUE);
    controller.onTurnChanged(PlayerColor.RED);

    System.out.println(log.toString());
    String[] lines = log.toString().split("\n");

    Assert.assertEquals("getPlayerRed", lines[0]);
    Assert.assertEquals("setTitle Player: RED (Waiting...)", lines[1]);
    Assert.assertEquals("refresh", lines[2]);
    Assert.assertEquals("setTitle Player: RED (Your Turn)", lines[3]);
    Assert.assertEquals("refresh", lines[4]);
    Assert.assertEquals("beginTurn", lines[5]);
  }
}

