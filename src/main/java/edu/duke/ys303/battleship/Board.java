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
  public T whatIsAt(Coordinate where);

  /**
   *General tryAddShip method for Board
   *
   *@param Ship to add
   *@return true when add successfully
   */
  public String tryAddShip(Ship<T> toAdd);
}













