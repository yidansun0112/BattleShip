package edu.duke.ys303.battleship;

import java.util.HashMap;

/**
 * This class implements from interface Ship. It handles information of a basic
 * ship
 */
public abstract class BasicShip<T> implements Ship<T> {

  /**
   * A HashMap to map Coordinates to Boolean to indicate status of a Ship.
   *
   * null: This Coordinate is not in the ship. false: This Coordinate is in the
   * Ship, and not hit. true: This Coordinate is in the Ship and is hit.
   */
  protected HashMap<Coordinate, Boolean> myPieces;

  /**
   * DisplayInfo class indicates how to display this ship.
   */
  protected ShipDisplayInfo<T> myDisplayInfo;

  protected ShipDisplayInfo<T> enemyDisplayInfo;

  
  
  /**
   * Get all of the Coordinates that this Ship occupies.
   * 
   * @return An Iterable with the coordinates that this Ship occupies
   */
  @Override
  public Iterable<Coordinate> getCoordinates() {
    return myPieces.keySet();
  }

  /**
   * Constructor of BasicShip.
   *
   * @param Set                of Coordinates for the Ship.
   * @param ShipDisplayInfo<T> indicates how to display this Ship.
   */
  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo,ShipDisplayInfo<T> enemyDisplayInfo) {
    myPieces = new HashMap<Coordinate, Boolean>();
    for (Coordinate c : where) {
      myPieces.put(c, false);
    }
    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = enemyDisplayInfo;
  }

  /**
   * A helpeer function to abstract out check if Coordinate c is part of a Ship.
   *
   * @param Coordinate to check.
   * @throws IllegalArgumentException when Coordinate is not part of this Ship
   */
  protected void checkCoordinateInThisShip(Coordinate c) {
    if (occupiesCoordinates(c) == false) {
      throw new IllegalArgumentException("Coordinate" + c.toString() + "is not part of this Ship.");
    }
  }

  /**
   * Override method of occupies. Indicate whether this ship occupies a
   * Coordinate.
   *
   * @param A Coordinate to check.
   * @return true if the ship contains this Coordinate, false otherwise.
   */
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    return myPieces.containsKey(where);
  }

  /**
   * Check if this ship has been hit in all of its locations meaning it has been
   * sunk.
   * 
   * @return true if this ship has been sunk, false otherwise.
   */
  @Override
  public boolean isSunk() {
    for (Coordinate c : myPieces.keySet()) {
      if (!wasHitAt(c)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Make this ship record that it has been hit at the given coordinate. The
   * specified coordinate must be part of the ship.
   * 
   * @param where specifies the coordinates that were hit.
   * @throws IllegalArgumentException if where is not part of the Ship
   */
  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.replace(where, true);
  }

  /**
   * Check if this ship was hit at the specified coordinates. The coordinates must
   * be part of this Ship.
   * 
   * @param where is the coordinates to check.
   * @return true if this ship as hit at the indicated coordinates, and false
   *         otherwise.
   * @throws IllegalArgumentException if the coordinates are not part of this
   *                                  ship.
   */
  @Override
  public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
  }

  /**
   * Return the view-specific information at the given coordinate. This coordinate
   * must be part of the ship.
   * 
   * @param where is the coordinate to return information for
   * @throws IllegalArgumentException if where is not part of the Ship
   * @return The view-specific information at that coordinate.
   */
  @Override
  public T getDisplayInfoAt(Coordinate where,boolean myShip) {
    // look up the hit status of this coordinate
    checkCoordinateInThisShip(where);
    if(myShip){
    return myDisplayInfo.getInfo(where, wasHitAt(where));
    }
    else{
      return enemyDisplayInfo.getInfo(where,wasHitAt(where));
    }
  }

  
}
