package edu.duke.ys303.battleship;

public class Placement {
  private final Coordinate where;
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
   * @return hashCode for Placement class.
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * Override equals method for Placement class.
   *
   * @param object to compare with.
   * @return true if two objects are equal, false else
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
