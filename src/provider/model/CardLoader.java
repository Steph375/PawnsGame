package provider.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;

/**
 * A class used for loading the cards from the deck.config file.
 */
public class CardLoader {

  /**
   * loads cards from the default path ( docs/deck.config)
   *
   * @return the cards.
   */
  public static ArrayList<Card> loadCards() {
    String path = "docs" + File.separator + "deck.config";
    return loadCards(path);
  }

  /**
   * loads the cards from a given path.
   *
   * @param path the path.
   * @return the card.s
   */
  public static ArrayList<Card> loadCards(String path) {
    ArrayList<Card> cards = new ArrayList<>();
    File config = new File(path);
    if (!config.exists()) {
      throw new FileSystemNotFoundException("deck.config not found");
    }

    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      String line = "";
      while (true) {
        Integer[][] influence = new Integer[5][5];
        line = br.readLine();
        if (line == null) {
          break;
        }
        String[] info = line.split(" ");
        String name = info[0];
        Integer cost = Integer.parseInt(info[1]);
        Integer value = Integer.parseInt(info[2]);
        // now the first line is parsed
        for (int i = 0; i < 5; i++) {
          line = br.readLine();
          for (int j = 0; j < 5; j++) {
            if (line.charAt(j) == 'X') {
              influence[i][j] = 0;
            } else if (line.charAt(j) == 'I') {
              influence[i][j] = 1;
            } else if (line.charAt(j) == 'C') {
              influence[i][j] = 0;
            }
          }
        }
        cards.add(new Card(influence, cost, value, name));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return cards;
  }
}
