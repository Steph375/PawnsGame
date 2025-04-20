package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Card;
import model.InfluencePawnCard;
import model.InfluencePosition;

public class EnhancedDeckReader extends DeckReader {

  public static List<Card> readDeck(File file) {
    List<Card> deck = new ArrayList<>();
    HashMap<String, Integer> cardCounts = new HashMap<>();

    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        Card card = makeCard(scanner); // this now refers to *this* class’s makeCard
        if (card != null) {
          String cardName = card.getName();
          int count = cardCounts.getOrDefault(cardName, 0);
          if (count >= 2) {
            throw new IllegalArgumentException("Card " + cardName + " appears more than twice in the deck.");
          }
          cardCounts.put(cardName, count + 1);
          deck.add(card);
        }
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found");
    }

    return deck;
  }

  protected static Card makeCard(Scanner scanner) {
    String headerLine = scanner.hasNextLine() ? scanner.nextLine().trim() : null;
    if (headerLine == null || headerLine.isEmpty()) return null;

    String[] parts = headerLine.split("\\s+");
    if (parts.length != 3) throw new IllegalArgumentException("Invalid header line");

    String name = parts[0];
    int cost = Integer.parseInt(parts[1]);
    int value = Integer.parseInt(parts[2]);
    String[] grid = makeBoard(scanner);

    return new InfluencePawnCard(name, cost, value, makeInfluence(grid));
  }

  protected static Map<InfluencePosition, InfluenceType> makeInfluence(String[] grid) {
    Map<InfluencePosition, InfluenceType> map = new HashMap<>();
    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        char ch = grid[r].charAt(c);
        int x = c - 2;
        int y = 2 - r;
        switch (ch) {
          case 'I':
            map.put(new InfluencePosition(x, y), InfluenceType.INFLUENCE);
            break;
          case 'U':
            map.put(new InfluencePosition(x, y), InfluenceType.UPGRADE);
            break;
          case 'D':
            map.put(new InfluencePosition(x, y), InfluenceType.DEVALUE);
            break;
          default:
            // Don't add X or anything else
            break;
        }
      }
    }
    return map;
  }
}
