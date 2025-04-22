package view;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import model.EnhancedPawnsGame;
import model.InfluencePawnCard;
import model.InfluencePosition;
import model.InfluenceType;
import model.PawnCard;
import model.PawnsGame;

public class EnhancedTextualViewTest {

  PawnsGame model;
  ArrayList<PawnCard> redDeck;
  ArrayList<PawnCard> blueDeck;
  TextualView view;

  @Before
  public void setUp() {
    model = new EnhancedPawnsGame(3, 5);

    Map<InfluencePosition, InfluenceType> redInfluence = new HashMap<>();
    redInfluence.put(new InfluencePosition(0, 1), InfluenceType.INFLUENCE);
    redInfluence.put(new InfluencePosition(1, 0), InfluenceType.UPGRADE);
    redInfluence.put(new InfluencePosition(2, 0), InfluenceType.DEVALUE);

    Map<InfluencePosition, InfluenceType> blueInfluence = new HashMap<>();
    blueInfluence.put(new InfluencePosition(0, 1), InfluenceType.INFLUENCE);
    blueInfluence.put(new InfluencePosition(-1, 0), InfluenceType.UPGRADE);
    blueInfluence.put(new InfluencePosition(-2, 0), InfluenceType.DEVALUE);

    InfluencePawnCard redTestCard = new InfluencePawnCard("Boost", 1, 3, redInfluence);
    InfluencePawnCard blueTestCard = new InfluencePawnCard("Boost", 1, 3, blueInfluence);

    redDeck = new ArrayList<>(Collections.nCopies(20, redTestCard));
    blueDeck = new ArrayList<>(Collections.nCopies(20, blueTestCard));

    model.setupGame(new ArrayList<>(redDeck), new ArrayList<>(blueDeck), 5, false);

    view = new EnhancedTextualView(model);
  }

  @Test
  public void testToString() {
    // check starting configuration
    Assert.assertEquals("0 1 _ _ _ 1  0\n"
            + "0 1 _ _ _ 1  0\n"
            + "0 1 _ _ _ 1  0", view.toString());

    model.placeCard(1, 0, redDeck.get(0));

    Assert.assertEquals("0 2 _ _ _ 1  0\n"
            + "3 R+0 +1 -1 _ 1  0\n"
            + "0 1 _ _ _ 1  0", view.toString());

    model.placeCard(1, 4, blueDeck.get(0));

    Assert.assertEquals("0 2 _ _ _ 2  0\n"
            + "3 R+0 +1 -2 +1 B+0  3\n"
            + "0 1 _ _ _ 1  0", view.toString());
  }
}