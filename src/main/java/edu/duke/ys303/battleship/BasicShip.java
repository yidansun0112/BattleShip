package edu.duke.ys303.battleship;

import java.util.HashMap;

/**
 * This class implements from interface Ship. It handles information of a basic
 * ship
 *
 * @param T Character
 */
public abstract class BasicShip<T> implements Ship<T> {

  /**
   * A HashMap to map Coordinates to Boolean to indicate status of a Ship.
   *
   * null: This Coordinate is not in the ship. false: This Coordinate is in
   * theShip, and not hit. true: This Coordinate is in the Ship and is hit.
   */
  protected HashMap<Coordinate, Boolean> myPieces;

  /** DisplayInfo class indicates how to display this ship for my own view. */
  protected ShipDisplayInfo<T> myDisplayInfo;

  /** DisplayInfo class indicates how to display this ship for enemy view. */
  protected ShipDisplayInfo<T> enemyDisplayInfo;

  /** Indicates whether this ship has been moved or not */
  protected boolean isMoved;

  /**
   * A HashMap to map order to coordinates to mark index of each piece in this
   * ship.
   */
  protected HashMap<Integer, Coordinate> coordIndex;

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
   * Get a coordinate at a specfied index.
   *
   * @param int for index
   * @return Coordinate
   */
  @Override
  public Coordinate findCoordinate(int i) {
    return coordIndex.get(i);
  }

  /**
   * Constructor of BasicShip.
   *
   * @param HashMap<Integer,Coordinate> index of Coordinates for the Ship.
   * @param ShipDisplayInfo<T>          indicates how to display this Ship for own
   *                                    view.
   * @param ShipDisplayInfo<T>          indicates how to display this Ship for
   *                                    enemy view.
   */
  public BasicShip(HashMap<Integer, Coordinate> coords, ShipDisplayInfo<T> myDisplayInfo,
      ShipDisplayInfo<T> enemyDisplayInfo) {
   myPieces = new HashMap<Coordinate, Boolean>();
    for (Integer i : coords.keySet()) {
      myPieces.put(coords.get(i), false);
    }
    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = enemyDisplayInfo;
    this.isMoved = false;
    this.coordIndex = coords;
  }

  @Override
  public void setMove(){
    this.isMoved=true;
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
   * @param Coordinate to check.
   * @return true if the ship contains this Coordinate, false otherwise.
   */
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    return myPieces.containsKey(where);
  }

  @Override
  public void transferHit(Ship<Character> s){

  }
  
  /**
   * Check if this ship has been hit in all of its locations, which means that it
   * has been sunk.
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
   * @param Coordinate specifies the coordinates that were hit.
   * @throws IllegalArgumentException if where is not part of the Ship
   */
  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.replace(where, true);
  }

  /**
   * Check if this ship was hit at the specified coordinates. The coordinate must
   * be part of this Ship.
   * 
   * @param where is the coordinates to check.
   * @return true if this ship was hit at the indicated coordinates, and false
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
   * must be part of the ship. If this ship was for own view, display own view.
   * Otherwise, display enemy view.
   * 
   * @param Coordinate to return information for
   * @param boolean    indicates whether this myShip has benn hit or not.
   * @throws IllegalArgumentException if where is not part of the Ship
   * @return T The view-specific information at that coordinate.
   */
  @Override
  public T getDisplayInfoAt(Coordinate where, boolean myShip) {
    // look up the hit status of this coordinate
    checkCoordinateInThisShip(where);
    if (myShip) {
      return myDisplayInfo.getInfo(where, wasHitAt(where));
    } else {
      return enemyDisplayInfo.getInfo(where, wasHitAt(where));
    }
  }

}
