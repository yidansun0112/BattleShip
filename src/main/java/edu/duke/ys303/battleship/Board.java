package edu.duke.ys303.battleship;

/**
 * Interface of basic Board
 *
 * @param T Character
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
   * General whatISAt given Coordinate position methof of Board. Self View.
   *
   * @return T content in this position
   */
  public T whatIsAtForSelf(Coordinate where);

  /**
   * General whatISAt given Coordinate position methof of Board. Enemy View.
   *
   * @return T content in this position
   */
  public T whatIsAtForEnemy(Coordinate where);

  /**
   * General tryAddShip method for Board
   *
   * @param Ship<T> to add
   * @return String null when add successfully, else message explains the problem.
   */
  public String tryAddShip(Ship<T> toAdd);

  /**
   * Search for any ship that occupies Coordinate c. If one is found, that Ship is
   * "hit" by the attack and record.
   *
   * @param Coordinate to attack on.
   * @return Ship<T> if one ship is hit, null otherwise.
   */
  public Ship<T> fireAt(Coordinate c);

  /**
   * Check whether all ships on the Board are sunk
   *
   * @return boolean true indicates all sunk, false otherwise.
   */
  public boolean allSunk();
}
