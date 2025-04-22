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

    Map<InfluencePosition, InfluenceType> influence = new HashMap<>();
    influence.put(new InfluencePosition(0, 1), InfluenceType.INFLUENCE);
    influence.put(new InfluencePosition(1, 0), InfluenceType.UPGRADE);
    influence.put(new InfluencePosition(-1, 0), InfluenceType.DEVALUE);

    InfluencePawnCard testCard = new InfluencePawnCard("Boost", 1, 3, influence);
    redDeck = new ArrayList<>(Collections.nCopies(20, testCard));
    blueDeck = new ArrayList<>(Collections.nCopies(20, testCard));

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

    Assert.assertEquals("0 2 _ _ _ 1  0 \n"
            + "0 R+0 +1 _ _ 1  0 \n"
            + "0 1 _ _ _ 1  0", view.toString());


  }
}