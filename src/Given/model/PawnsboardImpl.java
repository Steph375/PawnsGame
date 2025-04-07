package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Implementation of the model of the game of PawnsBoard.
 */
public class PawnsboardImpl implements Pawnsboard {

  ArrayList<ModelListener> listeners = new ArrayList<>();
  Integer rowSize;
  Integer colSize;
  Integer handSize;
  ArrayList<ArrayList<Object>> board;
  // inside the board, there can be empty spaces (null), pawns, and cards
  ArrayList<Card> leftPlayerHand;
  ArrayList<Card> rightPlayerHand;
  ArrayList<Card> leftPlayerDeck;
  ArrayList<Card> rightPlayerDeck;
  PlayerTeam nextTurn;
  boolean lastPlayerDidPassTurn = false;
  boolean gameOver = false;

  /**
   * Constructor for the model.
   *
   * @param rows     how many rows the game should have. Needs to be greater then 0.
   * @param cols     How many cols. Needs to be greater then 1, and not even.
   * @param handSize how big of a hand to have in this game.
   * @throws IllegalArgumentException arguments oob.
   */
  public PawnsboardImpl(Integer rows, Integer cols, Integer handSize) {
    if (rows <= 0 || cols <= 1 || cols % 2 == 0) {
      throw new IllegalArgumentException();
    }
    this.lastPlayerDidPassTurn = false;
    this.gameOver = false;
    this.nextTurn = PlayerTeam.RED;
    this.rowSize = rows;
    this.colSize = cols;
    this.handSize = handSize;
    this.board = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      board.add(new ArrayList<>(Collections.nCopies(colSize, null)));
    }
    this.leftPlayerHand = new ArrayList<>();
    this.rightPlayerHand = new ArrayList<>();
  }

  /**
   * Another constructor for the model.
   *
   * @param pawnsboard a game of Pawnsboard for future running of the model.
   */
  public PawnsboardImpl(Pawnsboard pawnsboard) {
    if (pawnsboard == null) {
      throw new IllegalArgumentException();
    }

    this.rowSize = pawnsboard.getRowSize();
    this.colSize = pawnsboard.getColSize();
    this.handSize = pawnsboard.getLeftPlayerHand().size();
    this.nextTurn = pawnsboard.getNextTurn();
    this.lastPlayerDidPassTurn = pawnsboard.isGameOver();
    this.gameOver = pawnsboard.isGameOver();

    this.board = new ArrayList<>();
    for (int i = 0; i < rowSize; i++) {
      ArrayList<Object> newRow = new ArrayList<>();
      for (int j = 0; j < colSize; j++) {
        Object piece = pawnsboard.getBoardPiece(i, j);
        if (piece instanceof Pawn) {
          newRow.add(new Pawn((Pawn) piece));
        } else if (piece instanceof Card) {
          newRow.add(new Card((Card) piece));
        } else {
          newRow.add(null);
        }
      }
      this.board.add(newRow);
    }

    this.leftPlayerHand = new ArrayList<>();
    for (Card card : pawnsboard.getLeftPlayerHand()) {
      this.leftPlayerHand.add(new Card(card));
    }

    this.rightPlayerHand = new ArrayList<>();
    for (Card card : pawnsboard.getRightPlayerHand()) {
      this.rightPlayerHand.add(new Card(card));
    }

    this.leftPlayerDeck = new ArrayList<>();
    for (Card card : pawnsboard.getLeftPlayerHand()) {
      this.leftPlayerDeck.add(new Card(card));
    }

    this.rightPlayerDeck = new ArrayList<>();
    for (Card card : pawnsboard.getRightPlayerHand()) {
      this.rightPlayerDeck.add(new Card(card));
    }
  }

  private static Integer[][] normalizeInfluenceGrid(Integer[][] influenceGrid, PlayerTeam team) {
    if (team == PlayerTeam.BLUE) {
      Integer[][] mirroredInfluence = new Integer[5][5];
      for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
          mirroredInfluence[i][4 - j] = influenceGrid[i][j];
        }
      }
      return mirroredInfluence;
    } else {
      return influenceGrid;
    }
  }

  /**
   * Add a listener for when the games state changes.
   *
   * @param listener the listener tp get called.
   */
  public void addListener(ModelListener listener) {
    listeners.add(listener);
  }

  /**
   * remove a listener from this model.
   *
   * @param listener to remove.
   */
  public void removeListener(ModelListener listener) {
    listeners.remove(listener);
  }

  private void stateChanged() {
    for (ModelListener listener : listeners) {
      listener.onModelChanged();
    }
  }

  /**
   * getter for the number of columns in this game.
   *
   * @return how many columns
   */
  @Override
  public Integer getColSize() {
    return colSize;
  }

  /**
   * getter for the number of rows.
   *
   * @return how many rows.
   */
  @Override
  public Integer getRowSize() {
    return rowSize;
  }

  /**
   * starts the game. Makes a deep copie of both decks.
   *
   * @param leftPlayerDeck  the deck for the left side player.
   * @param rightPlayerDeck the deck for the right side player.
   * @throws IllegalArgumentException if either params or null, different sizes, or the deck
   */
  @Override
  public void startGame(ArrayList<Card> leftPlayerDeck, ArrayList<Card> rightPlayerDeck) {
    if (leftPlayerDeck == null || rightPlayerDeck == null) {
      throw new IllegalArgumentException();
    }
    if (leftPlayerDeck.size() != rightPlayerDeck.size()) {
      throw new IllegalArgumentException();
    }
    if (this.handSize / 3 > leftPlayerDeck.size()) {
      throw new IllegalArgumentException();
    } // model invariant, a players hand size cannot exceed 1/3 of the deck size
    for (int i = 0; i < rowSize; i++) {
      board.get(i).set(0, new Pawn(1, PlayerTeam.RED));
      board.get(i).set(colSize - 1, new Pawn(1, PlayerTeam.BLUE));
    }

    this.leftPlayerDeck = new ArrayList<>();
    this.rightPlayerDeck = new ArrayList<>();
    for (int i = 0; i < leftPlayerDeck.size(); i++) {
      this.leftPlayerDeck.add(new Card(leftPlayerDeck.get(i)));
      this.rightPlayerDeck.add(new Card(rightPlayerDeck.get(i)));
    }
    drawCards();
    stateChanged();
  }

  @Override
  public PlayerTeam getNextTurn() {
    return nextTurn;
  }

  /**
   * processes the next player move. Which player move made the move is kept track of by pawnsboard
   * and alternates automatically.
   *
   * @param move The move object detailing the move. If the move type is pass, it is not necissary
   *             to set the numerical properties in move (handidx etc)
   */
  @Override
  public void makeMove(Move move) {
    this.makeMove(move, true, nextTurn);
  }


  /**
   * processes the next player move. Which player move made the move is kept track of by pawnsboard
   * and alternates automatically.
   *
   * @param move    The move object detailing the move. If the move type is pass, it is not
   *                necissary *             to set the numerical properties in move (handidx etc).
   * @param execute should the board be updated, set to false to "dry run" and test if the move is
   *                legal.
   */
  private void makeMove(Move move, Boolean execute, PlayerTeam team) {
    if (move == null) {
      throw new IllegalArgumentException();
    }
    PlayerTeam playerTeam;
    if (team == null) {
      playerTeam = nextTurn;
    } else {
      playerTeam = team;
    }

    if (move.moveType == MoveType.PASS) {
      if (lastPlayerDidPassTurn) {
        gameOver = true;
      } else {
        lastPlayerDidPassTurn = true;
      }
    }
    if (move.moveType == MoveType.PLACE) {
      lastPlayerDidPassTurn = false;
      if (move.placeCol == null || move.placeRow == null) {
        throw new IllegalArgumentException();
      }
      Card cardToPlace;
      try {
        if (playerTeam == PlayerTeam.RED) {
          cardToPlace = leftPlayerHand.get(move.handIdx);
        } else {
          cardToPlace = rightPlayerHand.get(move.handIdx);
        }
      } catch (IndexOutOfBoundsException e) {
        throw new IllegalArgumentException("hand idx oob");
      }

      Object cellContent = board.get(move.placeRow).get(move.placeCol);
      if (cellContent != null) {
        if (cellContent instanceof Pawn) {
          if (((Pawn) cellContent).getTeam() == playerTeam
              && ((Pawn) cellContent).getQuantity() >= cardToPlace.cost) {
            // card is legal to be placed
            if (execute) {
              cardToPlace.setOwnerTeam(nextTurn);
              board.get(move.placeRow).set(move.placeCol, cardToPlace);
              if (playerTeam == PlayerTeam.RED) {
                leftPlayerHand.remove((int) move.handIdx);
              } else {
                rightPlayerHand.remove((int) move.handIdx);
              }
              applyInfluence(cardToPlace, move.placeRow, move.placeCol);
            }

          } else {
            throw new IllegalArgumentException("insuficient pawns or wrong team");
          }
        } else if (cellContent instanceof Card) {
          throw new IllegalArgumentException("cant place a card on top of another card");
        }
      } else {
        throw new IllegalArgumentException("cant place a card on an empty cell");
      }
    }
    if (execute) {
      nextTurn = nextTurn == PlayerTeam.RED ? PlayerTeam.BLUE : PlayerTeam.RED;
      drawCards();
      stateChanged();
    }
  }

  /**
   * Applies the influence of a card that was just placed on the board.
   *
   * @param card      The card that was placed
   * @param centerRow The row where the card was placed
   * @param centerCol The column where the card was placed
   */
  private void applyInfluence(Card card, int centerRow, int centerCol) {
    Integer[][] influenceGrid = normalizeInfluenceGrid(card.influence, nextTurn);

    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (influenceGrid[i][j] == 1) {
          int boardRow = centerRow + (i - 2); // offset from center (2,2)
          int boardCol = centerCol + (j - 2);
          if (boardRow >= 0 && boardRow < rowSize && boardCol >= 0 && boardCol < colSize) {
            applyInfluenceToCell(boardRow, boardCol);
          }
          // off the board, nothing happens
        }
      }
    }
  }

  /**
   * applies influence to a specific cell on the board.
   *
   * @param row The row of the cell to influence
   * @param col The column of the cell to influence
   */
  private void applyInfluenceToCell(int row, int col) {
    Object cellContent = board.get(row).get(col);

    if (cellContent instanceof Card) {
      return;
    }

    // cell is empty, add a single pawn owned by current player
    if (cellContent == null) {
      Pawn newPawn = new Pawn(1);
      newPawn.setTeam(nextTurn);
      board.get(row).set(col, newPawn);
      return;
    }

    if (cellContent instanceof Pawn) {
      Pawn existingPawn = (Pawn) cellContent;

      if (existingPawn.getTeam() == nextTurn) {
        if (existingPawn.getQuantity() < 3) {
          existingPawn.setQuantity(existingPawn.getQuantity() + 1);
        }
      } else {
        existingPawn.setTeam(nextTurn);
      }
    }
  }


  /**
   * A method for scoring rows of the game.
   *
   * @param team Player, whose rows are scored.
   * @return a list of scores for each row.
   */
  public ArrayList<Integer> scoreRows(PlayerTeam team) {
    ArrayList<Integer> redResult = new ArrayList<>();
    ArrayList<Integer> bluResult = new ArrayList<>();
    for (int i = 0; i < rowSize; i++) {
      Integer redCounter = 0;
      Integer bluCounter = 0;
      for (int j = 0; j < colSize; j++) {
        if (board.get(i).get(j) != null && board.get(i).get(j) instanceof Card) {
          if (((Card) board.get(i).get(j)).getOwnerTeam() == PlayerTeam.RED) {
            redCounter += ((Card) board.get(i).get(j)).value;
          } else {
            bluCounter += ((Card) board.get(i).get(j)).value;
          }
        }
      }
      if (bluCounter > redCounter) {
        bluResult.add(bluCounter);
        redResult.add(0);
      } else if (redCounter > bluCounter) {
        redResult.add(redCounter);
        bluResult.add(0);
      } else if (redCounter == bluCounter) {
        redResult.add(0);
        bluResult.add(0);
      }
    }

    if (team == PlayerTeam.RED) {
      return redResult;
    } else if (team == PlayerTeam.BLUE) {
      return bluResult;
    }
    return null;
  }

  /**
   * Scores all the rows of the game and returns the scores for each row, with the index matched to
   * the returned list.
   *
   * @return the scores for the red player.
   */
  @Override
  public ArrayList<Integer> scoreRowsRightPlayer() {
    return scoreRows(PlayerTeam.BLUE);
  }

  /**
   * Scores all the rows of the game and returns the scores for each row, with the index matched to
   * the returned list.
   *
   * @return the scores for the blue player.
   */
  @Override
  public ArrayList<Integer> scoreRowsLeftPlayer() {
    return scoreRows(PlayerTeam.RED);
  }

  @Override
  public boolean isGameOver() {
    return gameOver;
  }

  /**
   * Gets the winning player.
   *
   * @return the winner, or null if a tie
   * @throws IllegalStateException if the game is not over
   */
  @Override
  public PlayerTeam getWinner() {
    if (!gameOver) {
      throw new IllegalStateException("game not over");
    }
    ArrayList<Integer> redRowScores = scoreRowsLeftPlayer();
    ArrayList<Integer> blueRowScores = scoreRowsRightPlayer();
    int redTotal = 0;
    int blueTotal = 0;
    for (int i = 0; i < redRowScores.size(); i++) {
      redTotal += redRowScores.get(i);
      blueTotal += blueRowScores.get(i);
    }
    if (redTotal > blueTotal) {
      return PlayerTeam.RED;
    } else if (blueTotal > redTotal) {
      return PlayerTeam.BLUE;
    } else {
      return null;
    }
  }

  @Override
  public Boolean isLegalMove(Move move, PlayerTeam team) {
    if (move != null) {
      try {
        makeMove(move, false, team);
      } catch (Exception e) {
        return false;
      }
    } else {
      throw new IllegalArgumentException();
    }
    return true;
  }

  @Override
  public ArrayList<Card> getLeftPlayerHand() {
    return leftPlayerHand;
  }

  @Override
  public ArrayList<Card> getRightPlayerHand() {
    return rightPlayerHand;
  }

  @Override
  public ArrayList<Card> getPlayerHand(PlayerTeam team) {
    if (team == PlayerTeam.RED) {
      return leftPlayerHand;
    } else if (team == PlayerTeam.BLUE) {
      return rightPlayerHand;
    }
    return null;
  }

  /**
   * Returns whats on the board at a given set of coordinates.
   *
   * @param row the row.
   * @param col the column.
   * @return whats on the board at that spot. Could be null (nothing), a card, or pawns.
   * @throws IllegalArgumentException arguments are oob.
   */
  @Override
  public Object getBoardPiece(int row, int col) {
    if (0 <= row && row < rowSize && 0 <= col && col < colSize) {
      return board.get(row).get(col);
    }
    throw new IllegalArgumentException();
  }

  private void drawCards() {
    if (leftPlayerHand.size() < handSize) {
      int cardsToDraw = handSize - leftPlayerHand.size();
      for (int i = 0; i < cardsToDraw; i++) {
        if (!leftPlayerDeck.isEmpty()) { // check deck is not empty
          leftPlayerHand.add(leftPlayerDeck.get(0));
          leftPlayerDeck.remove(0);
        }
      }
    }
    if (rightPlayerHand.size() < handSize) {
      int cardsToDraw = handSize - rightPlayerHand.size();
      for (int i = 0; i < cardsToDraw; i++) {
        if (!rightPlayerDeck.isEmpty()) {
          rightPlayerHand.add(rightPlayerDeck.get(0));
          rightPlayerDeck.remove(0);
        }
      }
    }

  }

  /**
   * returns a reference to the current game board. USE THIS FOR TESTING ONLY.
   *
   * @return the board
   */
  public ArrayList<ArrayList<Object>> getBoard() {
    return board;
  }

  /**
   * sets the board to whatever is supplied. USE THIS FOR TESTING ONLY, doesnt do any bounds
   * checks.
   *
   * @param board the board.
   */
  public void setBoard(ArrayList<ArrayList<Object>> board) {
    if (board == null) {
      throw new IllegalArgumentException();
    }
    this.board = board;
  }
}

