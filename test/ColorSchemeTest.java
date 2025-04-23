

import model.PlayerColor;
import view.ColorScheme;
import view.DefaultColor;
import view.HighContrast;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.assertEquals;

/**
 * Class to test the behavior of ColorSchemes.
 */
public class ColorSchemeTest {

  private ColorScheme defaultColor;
  private ColorScheme highContrast;

  @Before
  public void setup() {
    defaultColor = new DefaultColor();
    highContrast = new HighContrast();
  }



  @Test
  public void testDefaultBackgroundColor() {
    assertEquals(Color.decode("#F3EDED"), defaultColor.getBackgroundColor());
  }

  @Test
  public void testDefaultBoardBackground() {
    assertEquals(Color.GRAY, defaultColor.getBoardBackground());
  }

  @Test
  public void testDefaultPlayerColorRed() {
    assertEquals(Color.decode("#fca6ae"), defaultColor.getPlayerColor(PlayerColor.RED));
  }

  @Test
  public void testDefaultPlayerColorBlue() {
    assertEquals(Color.decode("#93bef3"), defaultColor.getPlayerColor(PlayerColor.BLUE));
  }

  @Test
  public void testDefaultLineColor() {
    assertEquals(Color.BLACK, defaultColor.getLineColor());
  }

  @Test
  public void testDefaultHighlightColor() {
    assertEquals(Color.CYAN, defaultColor.getHighlightColor());
  }

  @Test
  public void testDefaultTextColor() {
    assertEquals(Color.BLACK, defaultColor.getTextColor());
  }


  @Test
  public void testHighContrastBackgroundColor() {
    assertEquals(Color.decode("#F3EDED"), highContrast.getBackgroundColor());
  }

  @Test
  public void testHighContrastBoardBackground() {
    assertEquals(Color.BLACK, highContrast.getBoardBackground());
  }

  @Test
  public void testHighContrastPlayerColorRed() {
    assertEquals(Color.RED, highContrast.getPlayerColor(PlayerColor.RED));
  }

  @Test
  public void testHighContrastPlayerColorBlue() {
    assertEquals(Color.CYAN, highContrast.getPlayerColor(PlayerColor.BLUE));
  }

  @Test
  public void testHighContrastLineColor() {
    assertEquals(Color.WHITE, highContrast.getLineColor());
  }

  @Test
  public void testHighContrastHighlightColor() {
    assertEquals(Color.YELLOW, highContrast.getHighlightColor());
  }

  @Test
  public void testHighContrastTextColor() {
    assertEquals(Color.BLACK, highContrast.getTextColor());
  }
}
