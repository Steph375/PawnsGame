The purpose of this project is creating a game of Pawns Board. A 2 player game where players put
pawns and cards onto the field to gain points. The player with highest points wins. The more
in-depth rules of this game can be found in the file "RULES" (to be made later). To work on this
code, knowledge about writing controllers, GUIs, and general java knowledge is needed.
For example, take this snipper of code:
 @Test
  public void moveTest() {
    board.makeMove(new Move(MoveType.PLACE,0, 0, 0));
    assertEquals(cards.get(0), board.getBoardPiece(0, 0));
    board.makeMove(new Move(MoveType.PASS));
    assertNull(board.getBoardPiece(1, 1));
    assertFalse(board.isGameOver());
  }

This tests how moves in the game are made, it uses two important classes from the "model" package
Move and Pawnsboard. You can see that to make a move on the board, you will need to create an
instance of the class Move and set it MoveType, coordinates and id of the card in hand if the move
is a type of PLACE.

Key components of the CodeBase:
Pawnsboard is the class that represents the model itself. Through the methods of this class, the
game state is changed.
Controller classes are used for playing the game by giving commands that correspond with methods
in the model classes.
View classes render the board for the player to see the current game state.
Main classes are the game itself.

Subcomponents:
Other classes in the model package like Card, CardLoader, and Pawn correspond to the game objects
that are used by higher level classes, mainly Pawnsboard.

Organization and how to find components of the codebase:
controller package - controllers for the game
model package - game objects that are used by controllers and view classes. Also, the board class.
player package - objects for the players of the game.
view package - classes and interfaces that represent the rendering of the game state.
test packages - tests for public methods


Updates for HW6:
The model code has been refactored into a readonly and mutable interface. Other than that,
the changes for existing classes are pretty minor, mostly adding convenience methods. (eg in the
model there was a method getRightPlayerHand, and likewise for the left player. We added
getPlayerHand(player team) to make some code more convenient. In the process of refactoring the model
into a readonly interface, we added getters and setters, and changed the visibility of variables.

HW6:
For the extra credit, we implemented the board controll strategy.

New classes:
there is a new strategy interface, but its pretty self explanatory
PawnsBoardPanel draws the game board
GUIView handles gui setup and updates
CardPanel draws the current hand
PawnsBoardGraphController is the graphical controller

NOTE:
in the jar/ pawnboardgraphical main file, the first argument is the path to the deck of cards file