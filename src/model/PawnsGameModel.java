package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.ModelListener;

/**
 * The model for gameplay of PawnsBoard. The board is represented as a 2D array of BoardCell of the
 * given board dimensions. Positions are represented by the indices of a 2D array, so the position
 * 0,0 represents the top left of the board.
 */
public class PawnsGameModel implements PawnsGame {
  private final int rows;
  private final int cols;
  private BoardCell[][] board;
  private IPlayer playerRed;
  private IPlayer playerBlue;
  private boolean endGame;
  private int passes;
  private PlayerColor turn;
  private boolean hasStarted;
  //added field
  private final List<ModelListener> modelListeners;


  /**
   * Constructs a new game model with the given board dimensions.
   *
   * @param rows the number of rows on the board (must be > 0)
   * @param cols the number of columns on the board (must be > 1 and odd)
   * @throws IllegalArgumentException if the dimensions are invalid
   */
  public PawnsGameModel(int rows, int cols) {
    if (rows <= 0) {
      throw new IllegalArgumentException("Rows must be positive.");
    }
    if (cols <= 1 || cols % 2 == 0) {
      throw new IllegalArgumentException("Columns must be greater than 1 and odd.");
    }
    this.rows = rows;
    this.cols = cols;

    this.initializeBoard();

    // Red always starts.
    this.turn = PlayerColor.RED;
    this.endGame = false;
    this.passes = 0;
    this.hasStarted = false;
    this.modelListeners = new ArrayList<>();
  }


  /**
   * Creates the board represented by a 2D array of Cells. The Cells in the far left column are
   * owned by the red player, and each have one pawn and no cards. The Cells in the far right column
   * are owned by the Blue player, and each have one pawn and no cards. The cells in the middle have
   * null values for PlayerColor and Card, and 0 pawns, because they are empty cells.
   */
  private void initializeBoard() {
    this.board = new Cell[rows][cols];
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (c == 0) {
          // First column 1 red pawn in each
          this.board[r][c] = new Cell(1, null, PlayerColor.RED);
        } else if (c == cols - 1) {
          // Last column 1 blue pawn in each
          this.board[r][c] = new Cell(1, null, PlayerColor.BLUE);
        } else {
          this.board[r][c] = new Cell(0, null, null);
        }
      }
    }
  }

  /**
   * Starts the game not sure if it should imput rows and columns yet.
   *
   * @param deckOne  the list of cards for player one ( Red)
   * @param deckTwo  the list of cards for player two ( Blue)
   * @param handSize the number of cards to initially deal to each player;
   *                 cannot be greater than one third of the deck size
   */
  @Override
  public void setupGame(List<Card> deckOne, List<Card> deckTwo, int handSize, boolean shuffle) {
    int requiredCards = this.rows * this.cols;
    if (deckOne == null || deckOne.size() < (requiredCards + handSize)) {
      throw new IllegalArgumentException("Deck must have at least enough cards to fill the" +
              " board and provide an initial hand.");
    }
    if (deckTwo == null || deckTwo.size() < (requiredCards + handSize)) {
      throw new IllegalArgumentException("Deck must have at least enough cards to fill the" +
              " board and provide an initial hand.");
    }
    if (handSize <= 0 || handSize > (deckOne.size() / 3) || handSize > (deckTwo.size() / 3)) {
      throw new IllegalArgumentException("Invalid hand size.");
    }
    if (hasStarted) {
      throw new IllegalStateException("Game has started");
    }
    if (shuffle) {
      Collections.shuffle(deckOne);
      Collections.shuffle(deckTwo);
    }

    this.playerRed = new Player(PlayerColor.RED, deckOne, handSize);
    this.playerBlue = new Player(PlayerColor.BLUE, this.mirrorDeck(deckTwo), handSize);
    this.hasStarted = true;
  }

  /**
   * Mirrors the influence positions in each card.
   *
   * @param deck given deck that with have mirrored influence.
   * @return a new version of the deck with inverted x influence.
   */
  private List<Card> mirrorDeck(List<Card> deck) {
    ArrayList<Card> mirroredDeck = new ArrayList<>();
    for (Card c : deck) {
      mirroredDeck.add(c.mirrorInfluence());
    }
    return mirroredDeck;
  }

  /**
   * Checks if the move is legal so in the board parameters,
   * that it's the correct players turn, and that the player has enough pawns.
   *
   * @param row  the row index of the board cell
   * @param col  the column index of the board cell
   * @param card the card to be placed
   * @return boolean of if its legal
   */
  public boolean isLegalMove(int row, int col, Card card) {
    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      return false;
    }

    BoardCell cell = board[row][col];

    if (cell.getCard() != null) {
      return false;
    }

    if (cell.getPawns() <= 0) {
      return false;
    }

    if (cell.getColor() != this.turn) {
      return false;
    }

    return cell.getPawns() >= card.getCost();
  }

  /**
   * Places the specified card on the board at the given cell, if the move is legal.
   * Validate the move using  isLegalMove
   * Remove the appropriate pawns from the cell?
   * Place the card on the board and remove it from the current player's hand.
   * Apply the card's influence to the board (use private method)
   * Switch the turn to the opposing player.
   *
   * @param row  the row index where the card is to be placed
   * @param col  the column index where the card is to be placed
   * @param card the card to place
   * @throws IllegalArgumentException if the move is illegal
   */
  @Override
  public void placeCard(int row, int col, Card card) {
    if (!this.hasStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (card == null) {
      throw new IllegalArgumentException("Card cant be null");
    }
    if (!isLegalMove(row, col, card)) {
      throw new IllegalArgumentException("Illegal move");
    }

    BoardCell cell = this.board[row][col];
    cell.placeCard(card, this.turn);

    if (this.turn == PlayerColor.RED) {
      this.playerRed.removeCard(card);
    } else {
      this.playerBlue.removeCard(card);
    }

    this.applyCardInfluence(row, col, card);
    this.passes = 0;
    cell.removeAllPawns();
    this.drawCard();

    this.turn = (this.turn == PlayerColor.RED) ? PlayerColor.BLUE : PlayerColor.RED;

    for (ModelListener listener : modelListeners) {
      listener.onTurnChanged(this.turn);
    }
  }


  /**
   * Applys the card influence to each cell that the card has influence on.
   * influence position is relative to the card at the center 0,0.
   * so add and subtract to offset to the correct x and y.
   *
   * @param row  position of card y
   * @param col  position of card x
   * @param card the card being placed
   */
  private void applyCardInfluence(int row, int col, Card card) {
    List<InfluencePosition> influence = card.getInfluence();
    for (InfluencePosition offset : influence) {
      int posCol = col + offset.getX();
      int posRow = row - offset.getY();


      if (posRow < 0 || posRow >= this.rows || posCol < 0 || posCol >= this.cols) {
        continue;
      }

      board[posRow][posCol].addPawns(1, this.turn);
    }

  }

  /**
   * Processes a pass by the current player. If two passes ends game.
   */
  @Override
  public void passTurn() {
    if (!this.hasStarted) {
      throw new IllegalStateException("Game has not started");
    }

    this.passes++;


    if (this.passes >= 2) {
      this.endGame = true;

      int redScore = calculateTotalRedScore();
      int blueScore = calculateTotalBlueScore();
      PlayerColor winner = determineWinner(); // null = tie


      for (ModelListener listener : modelListeners) {
        listener.onGameOver(winner, redScore, blueScore);
      }
      return;
    }

    this.turn = (this.turn == PlayerColor.RED) ? PlayerColor.BLUE : PlayerColor.RED;

    for (ModelListener listener : modelListeners) {
      listener.onTurnChanged(this.turn);
    }
  }


  /**
   * Draws card from the current players decks and puts
   * it in the current players hand.
   */
  @Override
  public void drawCard() {
    if (!hasStarted) {
      throw new IllegalStateException("game has not started");
    }
    if (this.turn == PlayerColor.RED) {
      this.playerRed.drawCard();
    } else {
      this.playerBlue.drawCard();
    }
  }

  /**
   * Returns the color of the player whose turn it currently is.
   *
   * @return the current player's color
   */
  @Override
  public PlayerColor getCurrentPlayer() {
    return this.turn;
  }

  @Override
  public int calculateTotalScore(PlayerColor color) {
    if (!this.hasStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (color == PlayerColor.RED) {
      return calculateTotalRedScore();
    }
    return calculateTotalBlueScore();
  }

  /**
   * returns the row scores for red and blue in the given row.
   *
   * @param row the row index on the board.
   * @return an array where index 0 is red's score and index 1 is blue's score.
   */
  public int[] getRowScores(int row) {
    int redScore = 0;
    int blueScore = 0;
    for (int c = 0; c < cols; c++) {
      BoardCell cell = board[row][c];
      Card card = cell.getCard();
      if (card != null) {
        if (cell.getColor() == PlayerColor.RED) {
          redScore += card.getValueScore();
        } else if (cell.getColor() == PlayerColor.BLUE) {
          blueScore += card.getValueScore();
        }
      }
    }
    return new int[]{redScore, blueScore};
  }

  /**
   * calculates the total score for the red player.
   * For each row, if red's score is higher than blue's, red's score is added to red's total.
   *
   * @return the total score for the red player.
   */
  private int calculateTotalRedScore() {
    int totalRedScore = 0;
    for (int r = 0; r < rows; r++) {
      // index 0 is red index 1 is blue score
      int[] scores = getRowScores(r);
      if (scores[0] > scores[1]) {
        totalRedScore += scores[0];
      }
    }
    return totalRedScore;
  }

  /**
   * calculates the total score for the blue player.
   * For each row, if blue's score is higher than red's, blue's score is added to blue's total.
   *
   * @return the total score for the blue player.
   */
  private int calculateTotalBlueScore() {
    int totalBlueScore = 0;
    for (int r = 0; r < rows; r++) {
      //index 0 is red, index 1 is blue score
      int[] scores = getRowScores(r);
      if (scores[1] > scores[0]) {
        totalBlueScore += scores[1];
      }
    }
    return totalBlueScore;
  }

  /**
   * Determines the winner of the game based on the current total scores.
   *
   * @return the model.PlayerColor representing the winner, or null if the game is tied.
   */
  @Override
  public PlayerColor determineWinner() {
    if (!this.hasStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (!endGame) {
      throw new IllegalStateException("game is not over");
    }
    int redTotal = calculateTotalRedScore();
    int blueTotal = calculateTotalBlueScore();

    if (redTotal > blueTotal) {
      return PlayerColor.RED;
    } else if (blueTotal > redTotal) {
      return PlayerColor.BLUE;
    } else {
      // Return null to indicate a tie.
      return null;
    }
  }

  /**
   * Returns the current player's hand.
   *
   * @return a list of cards in the current player's hand
   */
  @Override
  public List<Card> getCurrentPlayerHand() {
    if (!this.hasStarted) {
      throw new IllegalStateException("The game has not started.");
    }

    if (this.turn == PlayerColor.RED) {
      return new ArrayList<>(this.playerRed.getHand());
    } else {
      return new ArrayList<>(this.playerBlue.getHand());
    }
  }

  /**
   * Gets current board.
   *
   * @return the current board.
   */
  public BoardCell[][] getBoard() {
    if (!this.hasStarted) {
      throw new IllegalStateException("game has not started");
    }

    BoardCell[][] boardCopy = new BoardCell[rows][cols];

    for (int row = 0; row < this.rows; row++) {
      for (int col = 0; col < this.cols; col++) {
        boardCopy[row][col] = new Cell(this.board[row][col].getPawns(),
                this.board[row][col].getCard(),
                this.board[row][col].getColor());
      }
    }

    return boardCopy;
  }

  /**
   * Returns true if the game has ended.
   *
   * @return true if both players have passed consecutively and the game is over.
   */
  public boolean isGameOver() {
    return endGame;
  }

  /**
   * Gets the redplayer in the game.
   *
   * @return IPlayer red player
   */
  public IPlayer getPlayerRed() {
    return this.playerRed;
  }

  /**
   * Gets the blue player in the game.
   *
   * @return Iplayer blue player
   */
  public IPlayer getPlayerBlue() {
    return this.playerBlue;
  }

  /**
   * Gets the height of the board.
   *
   * @return number of rows.
   */
  public int getHeight() {
    return this.rows;
  }

  /**
   * Checks how many times the players passes in a row.
   *
   * @return int value of the number of passes
   */
  @Override
  public int getPasses() {
    return this.passes;
  }

  /**
   * Gets the width of the board.
   *
   * @return number of columns.
   */
  public int getWidth() {
    return this.cols;
  }


  public void addModelListener(ModelListener listener) {
    this.modelListeners.add(listener);
  }

  public void startGame() {
    for (ModelListener l : modelListeners) {
      l.onTurnChanged(this.turn);
    }
  }


}
