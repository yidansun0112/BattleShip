package edu.duke.ys303.battleship;

/**
 * This class constructs placement of a ship.
 */
public class Placement {
  /** Coordinate indicates where to put the ship. */
  private final Coordinate where;
  /**
   * char indicates how to put this ship. 'V' means vertical. 'H' means
   * horizontal.
   */
  private final char orientation;

  /**
   * Getter for where
   *
   * @return where
   */
  public Coordinate getWhere() {
    return where;
  }

  /**
   * Getter for orientation.
   *
   * @return orientation
   */
  public char getOrientation() {
    return orientation;
  }

  /**
   * Constructor of Placement.
   *
   * @param Coordinate c
   * @param char       o
   */
  public Placement(Coordinate c, char o) {
    where = c;
    orientation = Character.toUpperCase(o);
  }

  /**
   * Consturctor of Placement.
   *
   * @param String s
   */
  public Placement(String s) {
    if (s.length() != 3) {
      throw new IllegalArgumentException("Placement should be of length 3, but now is " + s.length());
    }
    Coordinate c = new Coordinate(s.substring(0, 2));
    where = c;
    orientation = Character.toUpperCase(s.charAt(2));
  }

  /**
   * Override toString() method.
   *
   * @return String of Placement
   */
  @Override
  public String toString() {
    return "(" + where.toString() + "," + Character.toString(orientation) + ")";
  }

  /**
   * Override hashCode method for Placement class.
   *
   * Take use of hashCode of String from the result of toString()
   *
   * @return int hashCode for Placement class.
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * Override equals method for Placement class.
   *
   * @param Object to compare with.
   * @return boolean true if two objects are equal, false else
   */
  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Placement p = (Placement) o;
      return where.equals(p.where) && orientation == p.orientation;
    }
    return false;
  }

}
