package edu.duke.ys303.battleship;

import java.util.HashSet;

/**
 * This class extends from BasicShip and handles information of a rectangle
 * ship.
 */
public class RectangleShip<T> extends BasicShip<T> {
  /**
   * Name of this Ship.
   */
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
   * Convenience constructor of RectangleShip.
   *
   * @param String     name of this ship.
   * @param Coordinate upperLeft indicates upperleft of the Ship.
   * @param int        width indicates width of the Ship.
   * @param int        height indicates height of the Ship.
   * @param T          data indicates what to show when this Coordinate is not
   *                   hit.
   * @param T          onHit indicates what to show when this Coordinate is hit.
   */
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit),
        new SimpleShipDisplayInfo<T>(null, data));
  }

  /**
   * Convenience constructor of RectangleShip.
   * 
   * @param Coordinate upperLeft indicates upperleft of the Ship.
   * @param T          data indicates what to show when this Coordinate is not
   *                   hit.
   * @param T          onHit indicates what to show when this Coordinate is hit.
   */
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testship", upperLeft, 1, 1, data, onHit);
  }

  /**
   * Constructor of RectangleShip. Call super() to pass parameters to its parent
   * class.
   * 
   * @param String             name of this ship.
   * @param Coordinate         upperLeft indicates upperleft of the Ship.
   * @param int                width indicates width of the Ship.
   * @param int                height indicates height of the Ship.
   * @param ShipDisplayInfo<T> s indicates how to show this Ship.
   */
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> s1,
      ShipDisplayInfo<T> s2) {
    super(makeCoords(upperLeft, width, height), s1, s2);
    this.name = name;
  }

  /**
   * static method to make it easy to pass parameters to parent class BasicShip.
   * Make a set of Coordinates of this Ship.
   *
   * @param Coordinate upperleft indicates upperleft of the Ship.
   * @param int        width indicates width of this Ship.
   * @param int        height indicates height of this Ship.
   * @return HashSet<Coordinate> Set of all Coordinates of this Ship.
   */
  static HashSet<Coordinate> makeCoords(Coordinate upperleft, int width, int height) {
    HashSet<Coordinate> coords = new HashSet<Coordinate>();
    int r = upperleft.getRow();
    int c = upperleft.getColumn();
    for (int i = r; i < r + width; i++) {
      for (int j = c; j < c + height; j++) {
        Coordinate cdt = new Coordinate(i, j);
        coords.add(cdt);
      }
    }
    return coords;
  }

}
