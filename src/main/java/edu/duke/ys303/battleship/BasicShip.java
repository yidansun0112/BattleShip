package edu.duke.ys303.battleship;

import java.util.HashMap;

/**
 *This class implements from interface Ship. It handles information of a basic ship
 */
public abstract class BasicShip<T> implements Ship<T> {

  /**
   *A HashMap to map Coordinates to Boolean to indicate status of a Ship.
   *
   *null: This Coordinate is not in the ship.
   *false: This Coordinate is in the Ship, and not hit.
   *true: This Coordinate is in the Ship and is hit.
   */
  protected HashMap<Coordinate, Boolean> myPieces;

  /**
   *DisplayInfo class indicates how to display this ship.
   */
  protected ShipDisplayInfo<T> myDisplayInfo;

  /**
   *Constructor of BasicShip.
   *
   *@param Set of Coordinates for the Ship.
   *@param ShipDisplayInfo<T> indicates how to display this Ship.  
   */
  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo) {
    myPieces = new HashMap<Coordinate, Boolean>();
    for (Coordinate c : where) {
      myPieces.put(c, false);
    }
    this.myDisplayInfo = myDisplayInfo;
  }

  /**
   *Override method of occupies. Indicate whether this ship occupies a Coordinate.
   *
   *@param A Coordinate to check.
   *@return true if the ship contains this Coordinate, false otherwise.
   */
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    return myPieces.containsKey(where);
  }

  @Override
  public boolean isSunk() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    return false;
  }

  
  @Override
  public T getDisplayInfoAt(Coordinate where) {
    //  TODO this is not right We need to
    //  look up the hit status of this coordinate

    return myDisplayInfo.getInfo(where, false);
  }

}
