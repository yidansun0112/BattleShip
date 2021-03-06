package edu.duke.ys303.battleship;

import java.util.HashMap;

/**
 * This class handles construction of a BattleShip in version 2.
 *
 * @param T character
 */
public class BattleShip<T> extends BasicShip<T> {
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
   * Convenience constructor for BattleShip.
   *
   * @param String    name of this ship.
   * @param Placement for how to place this ship.
   * @param T         data for how to display this ship.
   * @param T         onHit indicates for how to display this ship when it was
   *                  hit.
   */
  public BattleShip(String name, Placement p, T data, T onHit) {
    this(name, p, new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
  }

  /**
   * Constructor for BattleShip. Pass parameters to parent class via super() call.
   *
   * @param String             name of this Ship.
   * @param Placement          for how to place this ship.
   * @param ShipDisplayInfo<T> for how to display this ship for self view.
   * @param ShipDisplayInfo<T> for how to display this ship for enemy view.
   */
  public BattleShip(String name, Placement p, ShipDisplayInfo<T> s1, ShipDisplayInfo<T> s2) {
    super(getCoords(p), s1, s2);
    this.name = name;
  }

  /**
   * Static method to make it easy to pass parameters to parent class BasicShip.
   * Make a map of Coordinates of this Ship based on the placement.
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
    coords.put(0, upperLeft);
    int r = upperLeft.getRow();
    int c = upperLeft.getColumn();
    for (int i = 1; i <= 3; i++) {
      Coordinate cdt = new Coordinate(r + 1, i + c - 2);
      coords.put(i, cdt);
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
    coords.put(0, new Coordinate(r + 1, c + 1));
    for (int i = 1; i <= 3; i++) {
      Coordinate cdt = new Coordinate(r - 1 + i, c);
      coords.put(i, cdt);
    }
    return coords;
  }

  /**
   * Static method to make it easy to pass parameters to parent class BasicShip.
   * Make a map of Coordinates of this Ship when placed down.
   * 
   * @param Coordinate indicates where to place this ship.
   * @return HashSet<Integer,Coordinate> Set of all Coordinates of this ship.
   */
  static HashMap<Integer, Coordinate> makeCoordsDown(Coordinate upperLeft) {
    HashMap<Integer, Coordinate> coords = new HashMap<Integer, Coordinate>();
    int r = upperLeft.getRow();
    int c = upperLeft.getColumn();
    coords.put(0, new Coordinate(r + 1, c + 1));
    for (int i = 1; i <= 3; i++) {
      Coordinate cdt = new Coordinate(r, c - 1 + i);
      coords.put(4 - i, cdt);
    }
    return coords;
  }

  /**
   * Static method to make it easy to pass parameters to parent class BasicShip.
   * Make a set of Coordinates of this Ship when placed left.
   * 
   * @param Coordinate indicates where to place this ship.
   * @return HashMap<Integer,Coordinate> Set of all Coordinates of this ship.
   */
  static HashMap<Integer, Coordinate> makeCoordsLeft(Coordinate upperLeft) {
    HashMap<Integer, Coordinate> coords = new HashMap<Integer, Coordinate>();
    int r = upperLeft.getRow();
    int c = upperLeft.getColumn();
    coords.put(0, new Coordinate(r + 1, c - 1));
    for (int i = 1; i <= 3; i++) {
      Coordinate cdt = new Coordinate(r - 1 + i, c);
      coords.put(4 - i, cdt);
    }
    return coords;
  }
}
