package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import model.Card;
import model.InfluencePosition;
import model.InfluenceType;
import model.PawnCard;

/**
 * This utility class features the public static method readDeck, and is intended for the creation
 * of decks of Cards given external text files in the correct format.
 */
public class DeckReader {

  /**
   * This method creates a deck of cards from the given text file.
   *
   * @param file the text file to create a deck of Cards from
   * @return the deck of Cards comprised of the cards in the text file
   * @throws IllegalArgumentException if the given file is not found,
   *                                  if the file has a given card more
   *                                  than two times, or the file is not correctly formatted.
   */
  public static List<Card> readDeck(File file) {
    List<Card> deck = new ArrayList<>();
    HashMap<String, Integer> cardCounts = new HashMap<>();

    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        PawnCard card = makeCard(scanner);
        if (card != null) {
          String cardName = card.getName();
          int count = cardCounts.getOrDefault(cardName, 0);
          if (count >= 2) {
            throw new IllegalArgumentException("Card appears more than twice in the deck.");
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

  private static PawnCard makeCard(Scanner scanner) {
    if (!scanner.hasNextLine()) return null;
    String headerLine = scanner.nextLine().trim();
    if (headerLine.isEmpty()) return null;

    String[] headerParts = headerLine.split("\\s");
    if (headerParts.length != 3) throw new IllegalArgumentException("Invalid Header");

    String name = headerParts[0];
    int cost = Integer.parseInt(headerParts[1]);
    int value = Integer.parseInt(headerParts[2]);

    return new PawnCard(name, cost, value, makeInfluence(makeBoard(scanner)));
  }

  protected static String[] makeBoard(Scanner scanner) {
    String[] board = new String[5];
    for (int i = 0; i < 5; i++) {
      if (!scanner.hasNextLine()) {
        throw new IllegalArgumentException("Not enough rows for card");
      }
      String row = scanner.nextLine().trim();
      if (row.length() != 5) {
        throw new IllegalArgumentException("Influence row must be 5 characters");
      }
      board[i] = row;
    }
    return board;
  }

  protected static Map<InfluencePosition, InfluenceType> makeInfluence(String[] grid) {
    Map<InfluencePosition, InfluenceType> map = new HashMap<>();
    int centerRow = 2, centerCol = 2;
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        char ch = grid[row].charAt(col);
        if (ch == 'I') {
          int offsetX = col - centerCol;
          int offsetY = centerRow - row;
          map.put(new InfluencePosition(offsetX, offsetY), InfluenceType.INFLUENCE);
        }
      }
    }
    return map;
  }
}
