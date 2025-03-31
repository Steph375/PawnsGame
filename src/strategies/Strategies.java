package strategies;




import model.PawnsGameReadOnly;


/**
 * Strategy interface that represent the strategies of the player playing Pawns Board game.
 */
public interface Strategies {

  /**
   * Function pick what moves is best selecting a row column and card that work best for that
   * Strategy class.
   * @param model takes in the model game to observe the current state of the game
   * @return  move which is a row column and card
   */
  Move chooseMove(PawnsGameReadOnly model);
}
