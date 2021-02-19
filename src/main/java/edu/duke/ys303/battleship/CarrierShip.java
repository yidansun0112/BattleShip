package edu.duke.ys303.battleship;

import java.util.HashMap;

/**
 * This class handles construction of a CarrierShip in version 2.
 *
 * @param T character
 */
public class CarrierShip<T> extends BasicShip<T> {
  /** Name of this Ship. */
  private final String name;

  /**
   * Name getter.
   *
   * @return String name.
   */
  public String getName() {
    return name;
  }

  /**
   * Convenience constructor for CarrierShip.
   *
   * @param String    name of this ship.
   * @param Placement for how to place this ship.
   * @param T         data for how to display this ship.
   * @param T         onHit indicates for how to display this ship when it was
   *                  hit.
   */
  public CarrierShip(String name, Placement p, T data, T onHit) {
    this(name, p, new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
  }

  /**
   * Constructor for CarrierShip. Pass parameters to parent class via super()
   * call.
   *
   * @param String             name of this Ship.
   * @param Placement          for how to place this ship.
   * @param ShipDisplayInfo<T> for how to display this ship for self view.
   * @param ShipDisplayInfo<T> for how to display this ship for enemy view.
   */
  public CarrierShip(String name, Placement p, ShipDisplayInfo<T> s1, ShipDisplayInfo<T> s2) {
    super(getCoords(p), s1, s2);
    this.name = name;
  }

  /**
   * Static method to make it easy to pass parameters to parent class BasicShip.
   * Make a map of Coordinates of this Ship based on the placement and location in
   * this ship.
   * 
   * @param Placement indicates how to place this ship.
   * @return HashMap<Integer,Coordinate> Set of all Coordinates of this ship.
   */
  static HashMap<Integer, Coordinate> getCoords(Placement p) {
    Character o = p.getOrientation();
    Coordinate c = p.getWhere();
    if (o == 'U') {
      return makeCoordsUp(c);
    } else if (o == 'R') {
      return makeCoordsRight(c);
    } else if (o == 'D') {
      return makeCoordsDown(c);
    } else {
      return makeCoordsLeft(c);
    }
  }

  /**
   * Static method to make it easy to pass parameters to parent class BasicShip.
   * Make a map of Coordinates of this Ship when placed up.
   * 
   * @param Coordinate indicates where to place this ship.
   * @return HashMap<Integer,Coordinate> Set of all Coordinates of this ship.
   */
  static HashMap<Integer, Coordinate> makeCoordsUp(Coordinate upperLeft) {
    HashMap<Integer, Coordinate> coords = new HashMap<Integer, Coordinate>();
    int r = upperLeft.getRow();
    int c = upperLeft.getColumn();
    int count = 0;
    for (int i = r; i <= r + 4; i++) {
      if (i <= r + 2) {
        Coordinate cdt = new Coordinate(i, c);
        coords.put(count, cdt);
        count++;
      }
      if (i >= r + 2) {
        Coordinate cdt = new Coordinate(i, c + 1);
        coords.put(count, cdt);
        count++;
      }
    }
    return coords;
  }

  /**
   * Static method to make it easy to pass parameters to parent class BasicShip.
   * Make a map of Coordinates of this Ship when placed right.
   * 
   * @param Coordinate indicates where to place this ship.
   * @return HashMap<Integer,Coordinate> Set of all Coordinates of this ship.
   */
  static HashMap<Integer, Coordinate> makeCoordsRight(Coordinate upperLeft) {
    HashMap<Integer, Coordinate> coords = new HashMap<Integer, Coordinate>();
    int r = upperLeft.getRow();
    int c = upperLeft.getColumn();
    int count = 0;
    for (int i = c - 2; i <= c + 2; i++) {
      if (i <= c) {
        Coordinate cdt = new Coordinate(r + 1, i);
        coords.put(count, cdt);
        count++;
      }
      if (i >= c) {
        Coordinate cdt = new Coordinate(r, i);
        coords.put(count, cdt);
        count++;
      }
    }
    return coords;
  }

  /**
   * Static method to make it easy to pass parameters to parent class BasicShip.
   * Make a map of Coordinates of this Ship when placed down.
   * 
   * @param Coordinate indicates where to place this ship.
   * @return HashMap<Integer,Coordinate> Set of all Coordinates of this ship.
   */
  static HashMap<Integer, Coordinate> makeCoordsDown(Coordinate upperLeft) {
    HashMap<Integer, Coordinate> coords = new HashMap<Integer, Coordinate>();
    int r = upperLeft.getRow();
    int c = upperLeft.getColumn();
    int count = 5;
    for (int i = r; i <= r + 4; i++) {
      if (i <= r + 2) {
        Coordinate cdt = new Coordinate(i, c);
        coords.put(count, cdt);
        count--;
      }
      if (i >= r + 2) {
        Coordinate cdt = new Coordinate(i, c - 1);
        coords.put(count, cdt);
        count--;
      }
    }
    return coords;
  }

  /**
   * Static method to make it easy to pass parameters to parent class BasicShip.
   * Make a map of Coordinates of this Ship when placed left.
   * 
   * @param Coordinate indicates where to place this ship.
   * @return HashMap<Integer,Coordinate> Set of all Coordinates of this ship.
   */
  static HashMap<Integer, Coordinate> makeCoordsLeft(Coordinate upperLeft) {
    HashMap<Integer, Coordinate> coords = new HashMap<Integer, Coordinate>();
    int r = upperLeft.getRow();
    int c = upperLeft.getColumn();
    int count = 5;
    for (int i = c; i <= c + 4; i++) {
      if (i <= c + 2) {
        Coordinate cdt = new Coordinate(r, i);
        coords.put(count, cdt);
        count--;
      }
      if (i >= c + 2) {
        Coordinate cdt = new Coordinate(r + 1, i);
        coords.put(count, cdt);
        count--;
      }
    }
    return coords;
  }
}
