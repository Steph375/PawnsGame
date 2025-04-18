package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.Card;
import model.InfluencePawnCard;
import model.InfluencePosition;

/**
 * A deck reader that supports UPGRADE ('U') and DEVALUE ('D') influence types.
 */
public class EnhancedDeckReader extends DeckReader {


  /**
   * Makes an InfluencePawnCard from the current position of the scanner.
   *
   * @param scanner The Scanner positioned at the card header.
   * @return A fully constructed InfluencePawnCard.
   */
  protected static Card makeCard(Scanner scanner) {
    String headerLine = null;
    if (scanner.hasNextLine()) {
      headerLine = scanner.nextLine().trim();
    }
    if (headerLine == null || headerLine.isEmpty()) {
      return null;
    }

    String[] headerParts = headerLine.split("\\s+");
    if (headerParts.length != 3) {
      throw new IllegalArgumentException("Invalid Header");
    }

    String name = headerParts[0];
    int cost = 0;
    int value = 0;
    try {
      cost = Integer.parseInt(headerParts[1]);
      value = Integer.parseInt(headerParts[2]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Cost and value must be integers");
    }

    return new InfluencePawnCard(name, cost, value, makeInfluence(makeBoard(scanner)));
  }
  /**
   * Overrides the default influence parsing to include U and D.
   */
  protected static Map<InfluencePosition, InfluenceType> makeInfluence(String[] grid) {
    Map<InfluencePosition, InfluenceType> map = new HashMap<>();
    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        char ch = grid[r].charAt(c);
        int x = c - 2;
        int y = 2 - r;

        InfluenceType type = null;
        switch (ch) {
          case 'I':
            type = InfluenceType.INFLUENCE;
            break;
          case 'U':
            type = InfluenceType.UPGRADE;
            break;
          case 'D':
            type = InfluenceType.DEVALUE;
            break;
          default:
           break;
        }

        map.put(new InfluencePosition(x, y), type);
      }
    }
    return map;
  }
}
