package edu.duke.ys303.battleship;

/**
 * Interface of basic Board
 */

public interface Board<T> {

  /**
   * General getWidth method of Board.
   *
   * @return width of the board.
   */
  public int getWidth();

  /**
   * General getWidth method of Board.
   * 
   * @return width of the board.
   */
  public int getHeight();

  /**
   *General whatISAt given Coordinate position methof of Board
   *
   *@return Content in this position
   */
  public T whatIsAtForSelf(Coordinate where);

  public T whatIsAtForEnemy(Coordinate where);
  
  /**
   *General tryAddShip method for Board
   *
   *@param Ship to add
   *@return true when add successfully
   */
  public String tryAddShip(Ship<T> toAdd);

  /**
   *Search for any ship that occupies Coordinate c.
   *If one is found, that Ship is "hit" by the attack and record.
   *
   *@param Coordinate to attack on.
   *@return Ship<T> if one ship is hit, null otherwise.
   */
  public Ship<T> fireAt(Coordinate c);
}













