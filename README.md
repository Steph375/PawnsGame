## Overview
* In this project, we created the model and a textual view for a two player game called PawnsBoard.
  In our model, the board is represented by a 2D array of Cell objects, which have behavior visible
  through the public interface BoardCell, which the Cell class implements. These objects keep track
  of the player in possession of the cell, the number of pawns in the cell, and the card placed in
  the cell.
* The game is played using cards, specifically the PawnCard class with behavior made public by
  implementing the Card interface.
* Decks of cards are created by reading external files in the format provided in the specification.
  This is done using the controller.DeckReader utility class, and files that are not correctly formatted throw
  IllegalArgumentExceptions, while blank files create an empty deck.

## Class Invariants
* In the model, the dimensions of the board are class invariants:
  * The number of columns of the board must be odd and greater than or equal to 3.
  * The number of rows in the board must be positive.
* The number of pawns that can be in a cell on the board is greater than or equal to 0, and less than
  or equal to 3.
* The game is not over unless both players have passed.
* A cell on the board can have at most one card placed in it.
* A player's color is either Red or Blue.
* A player cannot place a card in a cell that they don't possess.
* A player's deck must be large enough to fill the entire board and their hand.
* A player's hand cannot be greater than 1/3 the size of the board.

## Quick Start
* To start playing the game, the user would first create a new PawnsGameModel object.
* Then, the user would use the PawnsGameModel’s `startGame` method, passing in the decks each player
  will play with. These decks can be created using `controller.DeckReader.readDeck(File f)`, with `f` being the
  text file representing the cards in the deck.


**Sample code:**

// create file

`String path = "docs" + File.separator + "deck.config";`

`File configFile = new File(path);`

// Read the deck from the configuration file.

`List<Card> deck = controller.DeckReader.readDeck(configFile);`

`PawnsGameModel model = new PawnsGameModel(3, 5);`

// Use the same deck for both players with a starting hand size of 5 and no shuffling.

`model.startGame(new ArrayList<Card>(deck), new ArrayList<Card>(deck), 5, false);`

// Create the textual view.

`TextualView view = new PawnsTextualView(model);`


## Key Components
* The **PawnsGameModel** is a model for the game, and represents the current state of the game and offers
  methods for the modification of the game state.
* The **PawnsTextualView** is a textual representation of the game model, allowing the user to visualize
  card placement and pawn counts in different cells of the board, as well as scoring of the game.

## Key Subcomponents
* **PawnCards** are the cards that are used to play the game, and influence and change the board that
  the game is played on, as well as informing the scoring of the game.
* The board is comprised of **Cells**, which track information like what card is placed in that cell,
  the number of pawns in that cell, and the player (red or blue) that is in possession of that cell.
  It also offers methods to change these states.
* The model uses **Players** to store information about each (red or blue) player's hand and deck
  as well as methods to observe this information and change the player's hand and deck.


* The **controller.DeckReader** is a utility class that allows the creation of card decks from outside configuration
  files, and its use is essential for starting the game.

## Source Organization
* The **src** directory contains:
  * A **model** package that contains the game's model and classes and interfaces that enable it's
    implementation.
  * A **view** package that contains the game's textual view.
  * A **PawnsBoard** class that contains a main method demonstrating gameplay.
* The **test** directory contains:
  * Classes testing the behavior of the different components of the source code.
  * A **testConfigFiles** package of txt files used to test the behavior of the controller.DeckReader.
* The **docs** directory contains a configuration file for the creation of a sample card deck to be
  created with the controller.DeckReader.
* The **controller.DeckReader** is placed outside the model because we feel that it is more so a
  part of the controller and therefore the model doesn't need to have access to it since it
  doesn't interact with it directly ever.


## HW 6
### Changes for part 2
* Ensured that the getBoard method in the PawnsGameModel returns a copy of the board, not an array
  with the same Cell objects in order to prevent unwanted editing of the actual game board.
* Made getRowScores public in the PawnsGameReadOnly interface. This method produces an array of
  integers, with index 0 representing Red's score and index 1 representing Blue's score, so both player's
  scores can be accessed with one method call.

### New Classes
* Classes for each strategy are stored in the **strategies** package
* The StrategiesTest class for testing Strategies is in the **test** directory
* The PawnsBoardGame class, outside the MVC, contains a main method to start the game's GUI
* Mock models **MockPawnsGame** and **MockPawnsGameWithBoard** for testing Strategies in the 
  **test** folder

### Extra Credit
* Extra credit Strategies can be found in the **strategies** package, outside the MVC, alongside a
  **MultipleStrategies** class that allows for the combination of two or more Strategies.
* Tests for every strategy can be found in the StrategiesTest class in the test directory.

### JAR file
* Our JAR file is titled HW6.jar and can be found in the outermost level of our project (where the 
  README is). 
* The file has been tested and runs correctly only from this location-- when placed in 
  a different folder, an exception will be thrown because the DeckReader won't be able to find the
  configuration file to create the deck.
* There are two windows (one for red, one for blue) as specified. When running this JAR or our main
  method, the windows are made directly on top of each other so to see both you need to drag the top
  one out of the way.

### Screenshots and .txt files
* The four required screenshots can be found on the same organizational level as the README, named
  to identify which stage of gameplay they represent.
* .txt files transcribing Strategies 1 and 2 can be found in the same location


## HW 7
### Changes for part 3
* Ensured that cards could not be selected in a player's GUI window if it's not that player's turn.
* Changed the name of the startGame method in the model interface to setUpGame to more accurately
  describe the behavior of that method.
* In the model, added a `addModelListener` method to add a ModelListener to the model
* In the model added a new `startGame` method that notifies the ModelListeners that it's the first
  player's turn.
* We updated the main method in **PawnsBoardGame** to use the new controller
* We changed the game screenshots to reflect the new label at the top that indicates who's turn it 
  is

### New Classes
* We created an interface **ActionPlayer** representing a player that can take actions in our game
  this interface is found in the **player** package, and has a 
* We created a **MachinePlayer** implementation of that interface, also in the **player** package
  to handle the behavior of an AI player when it's their turn to play.
* We created a **HumanPlayer** implementation of that interface in the same **player** package.
  This implementation does nothing in the startTurn method, as input from the human player will
  be delivered to the controller directly from user interaction with the GUI.
* We created a **PlayerCreator** class in the **player** package that contains the static factory 
  method build(PlayerColor, String) which creates a player of the specified color and type (human,
  or the Strategy to be used by an AI player)


* Added a **ModelListener** interface to the **controller** package to serve as an observer of
  the changing state of the model. Specifically, when the turn changes and when the game ends.
* Created a **GameController** class in the **controller** package that moderates interactions 
  between the GUI and the model.


* created new classes for testing and mocking components in the **test** directory:
  * ControllerTest
  * MachinePlayerTest
  * MockActionPlayer
  * MockModel
  * MockPlayer
  * MockView

### Main / Updated JAR
* The main method for PawnsBoardGame (and the JAR that runs it) takes arguments from the command
  line in the form: <red deck file path> <blue deck file path> <red player type> <blue player type>
* type means either "human" or the name of the strategy:
  * 'strategy1' will create an AI player using the FillFirst strategy
  * 'strategy2' will create an AI player using the ControlBoard strategy
  * 'strategy3' will create an AI player using the MaximizeScore strategy
  * 'strategy4' will create an AI player using the NoGoodMove strategy
  * The AI player can also use MultipleStrategies. These are inputted in a comma separated list.
    For example, 'strategy2, strategy1'
* Notably, the main method ends with `model.startGame();`. Without this method call, the controller 
  won't know that it's the red player's turn, and interacting with either frame will produce an 
  error message that it's not your turn.