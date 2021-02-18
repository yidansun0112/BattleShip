package edu.duke.ys303.battleship;

/**
 * This class handles a basic Ship interface.
 *
 * @param T Character
 */
public interface Ship<T> {

  /**
   * Get all of the Coordinates that this Ship occupies.
   * 
   * @return An Iterable with the coordinates that this Ship occupies
   */
  public Iterable<Coordinate> getCoordinates();

  /**
   * Get a coordinate at a specfied index.
   *
   * @param int for index
   * @return Coordinate
   */
  public Coordinate findCoordinate(int i);

  public void transferHit(Ship<Character> s);

  public void setMove();

  public Iterable<Integer> getIndex();

  /**
   * Check if this ship occupies the given coordinate.
   * 
   * @param Coordinate where is the Coordinate to check if this Ship occupies
   * @return true if where is inside this ship, false if not.
   */
  public boolean occupiesCoordinates(Coordinate where);

  /**
   * Check if this ship has been hit in all of its locations meaning it has been
   * sunk.
   * 
   * @return true if this ship has been sunk, false otherwise.
   */
  public boolean isSunk();

  /**
   * Make this ship record that it has been hit at the given coordinate. The
   * specified coordinate must be part of the ship.
   * 
   * @param Coordinate where specifies the coordinates that were hit.
   * @throws IllegalArgumentException if where is not part of the Ship
   */
  public void recordHitAt(Coordinate where);

  /**
   * Check if this ship was hit at the specified coordinates. The coordinates must
   * be part of this Ship.
   * 
   * @param Coordinate where is the coordinates to check.
   * @return true if this ship as hit at the indicated coordinates, and false
   *         otherwise.
   * @throws IllegalArgumentException if the coordinates are not part of this
   *                                  ship.
   */
  public boolean wasHitAt(Coordinate where);

  /**
   * Return the view-specific information at the given coordinate. This coordinate
   * must be part of the ship.
   * 
   * @param Coordinate where is the coordinate to return information for
   * @param boolean    indicates whether for self view or enemy view.
   * @throws IllegalArgumentException if where is not part of the Ship
   * @return The view-specific information at that coordinate.
   */
  public T getDisplayInfoAt(Coordinate where, boolean myShip);

  /**
   * Get the name of this Ship, such as "submarine".
   * 
   * @return String the name of this ship
   */
  public String getName();

}
