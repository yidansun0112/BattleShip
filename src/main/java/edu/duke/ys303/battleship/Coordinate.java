package edu.duke.ys303.battleship;

public class Coordinate {
  private final int row;
  private final int column;

  /**
   * Getter for row.
   *
   * @return row
   */
  public int getRow() {
    return row;
  }

  /**
   * Getter for column.
   *
   * @return column
   */
  public int getColumn() {
    return column;
  }

  /**
   * Constructor(int, int) for Coordinate class.
   *
   * @param r value of row
   * @param c value of column
   */
  public Coordinate(int r, int c) {
    row = r;
    column = c;
  }

  /**
   * Coordinate(Sting) for coordinate class.
   *
   * @param descr String to construct the Coordinate class. String should be of
   *              length two, the first character should be a letter, and the
   *              second should be a number.
   * @throws IllegalArgumentException if the String is in wrong format.
   */
  public Coordinate(String descr) {
    if (descr.length() != 2) {
      throw new IllegalArgumentException(
          "Coordinate String must contain only two characters, but now the length is" + descr.length());
    }
    String scr = descr.toUpperCase();
    char rowLetter = scr.charAt(0);
    char colLetter = scr.charAt(1);
    if (rowLetter < 'A' || rowLetter > 'Z') {
      throw new IllegalArgumentException("Row letter should be within 'A'--'Z', but now is " + rowLetter);
    }
    row = rowLetter - 'A';
    if (colLetter < '0' || colLetter > '9') {
      throw new IllegalArgumentException("Column letter should be within '0'--'9', but now is " + colLetter);
    }
    column = colLetter - '0';
  }

  /**
   * Override equals for Coordinate class.
   *
   * @param o object to compare
   * @return true if equal, false else.
   */
  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) o;
      return row == c.row && column == c.column;
    }
    return false;
  }

  /**
   * Override toString() for Coordinate class.
   *
   * @return String of the coordinate.
   */
  @Override
  public String toString() {
    return "(" + row + "," + column + ")";
  }

  /**
   * Override hashCode() for Coordinate class.
   *
   * Take use of hashcode of String class.
   *
   * @return hashCode for Coordinate class.
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
