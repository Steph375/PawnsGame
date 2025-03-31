package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import model.Card;
import model.InfluencePosition;
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
    // Map to track the count of each card by name.
    HashMap<String, Integer> cardCounts = new HashMap<>();

    try {
      Scanner scanner = new Scanner(file);

      while (scanner.hasNextLine()) {
        PawnCard card = makeCard(scanner);
        if (card != null) {
          // Enforce that no card appears more than twice.
          String cardName = card.getName();
          int count = cardCounts.getOrDefault(cardName, 0);
          if (count >= 2) {
            scanner.close();
            throw new IllegalArgumentException("Card appears more than twice in the deck.");
          }
          cardCounts.put(cardName, count + 1);

          deck.add(card);
        }
      }

      scanner.close();
      return deck;
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found");
    }
  }

  /**
   * Makes the individual cards based on the scanner given.
   *
   * @param scanner the scanner from the main method where the card firs starts.
   * @return a pawn card with all the info from the being collected from file.
   */
  private static PawnCard makeCard(Scanner scanner) {
    String headerLine = null;
    if (scanner.hasNextLine()) {
      headerLine = scanner.nextLine().trim();
    }
    if (headerLine == null || headerLine.isEmpty()) {
      return null;
    }

    String[] headerParts = headerLine.split("\\s");
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

    return new PawnCard(name, cost, value, makeInfluence(makeBoard(scanner)));
  }

  /**
   * Creates the influence board for each of the cards.
   *
   * @param scanner gets the scanner once it hits the influence board from the method above.
   * @return a string array of the positions
   */
  private static String[] makeBoard(Scanner scanner) {
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

  /**
   * Creates the HashMap representation of the card's influence on the board.
   *
   * @param grid String array representation of the influence positions.
   * @return A Hashmap of all InfluencePositions and whether they have influence or not.
   */
  private static HashMap<InfluencePosition, Boolean> makeInfluence(String[] grid) {
    HashMap<InfluencePosition, Boolean> map = new HashMap<>();

    int centerRow = 2;
    int centerCol = 2;
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        char ch = grid[row].charAt(col);
        // Makes the center (0,0)
        int offsetX = col - centerCol;
        int offsetY = centerRow - row;
        // Check if card has influence on this position
        boolean hasInfluence = (ch == 'I');
        map.put(new InfluencePosition(offsetX, offsetY), hasInfluence);
      }
    }
    return map;
  }
}
