## User-player Interface Design
The primary behavior of a user player is to play a turn in the game. To implement this behavior, we
would create a player Interface with different classes that implement it representing either a human
or AI player. The interface would represent this behavior with a public `playTurn()` method. A human
player would play their turn by interacting with the controller through the game's GUI. An AI player 
would rely on some other automated process to interact with the different possible moves.
Other methods may include check if a player can actually play their turn `canPlay()` or
having the player recieve the current player recieve the state of the game.
