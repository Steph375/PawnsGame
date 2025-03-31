import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.InfluencePosition;

/**
 * Tests Influence Position Class.
 */
public class InfluencePositionTest {

  private InfluencePosition pos1;
  private InfluencePosition pos2;
  private InfluencePosition pos3;
  private InfluencePosition diffX;
  private InfluencePosition diffY;

  @Before
  public void setUp() {
    pos1 = new InfluencePosition(1, 2);
    pos2 = new InfluencePosition(1, 2);
    pos3 = new InfluencePosition(1, 2);
    diffX = new InfluencePosition(2, 2);
    diffY = new InfluencePosition(1, 3);
  }

  @Test
  public void testGetters() {
    Assert.assertEquals(1, pos1.getX());
    Assert.assertEquals(2, pos1.getY());
  }

  @Test
  public void testEqualsReflexive() {
    Assert.assertEquals(pos1, pos1);
    Assert.assertEquals(pos1, pos2);
    Assert.assertEquals(pos2, pos1);
    Assert.assertEquals(pos1, pos2);
    Assert.assertEquals(pos2, pos3);
    Assert.assertEquals(pos1, pos3);
    Assert.assertNotEquals(pos1, diffX);
    Assert.assertNotEquals(pos1, diffY);
  }


  @Test
  public void testHashCodeEquality() {
    Assert.assertEquals(pos1.hashCode(), pos2.hashCode());
  }

}
