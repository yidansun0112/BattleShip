package edu.duke.ys303.battleship;

import java.util.ArrayList;

public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  final ArrayList<Ship<T>> myShips;

  /**
   * Get width of the board.
   *
   * @return width of the board.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Get height of the board.
   * 
   * @return height of the board.
   */
  public int getHeight() {
    return height;
  }

  /**
   * Constructs a BattleShipBoard with the specified width and height
   * 
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */

  public BattleShipBoard(int w, int h) {
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
    myShips = new ArrayList<Ship<T>>();
  }

  /**
   * Try to add a ship into ArrayList myShips
   *
   * @param toAdd A ship to add.
   * @return true when add successfully.
   */
  public boolean tryAddShip(Ship<T> toAdd) {
    myShips.add(toAdd);
    return true;
  }

  /**
   * Takes a Coordinate, and sees if any ship occupies that coordinate.
   *
   * @param A coordinate to check.
   * @return dispalyInfo of the ship, if one is found. NULL, when no one is found.
   */
  public T whatIsAt(Coordinate where) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where);
      }
    }
    return null;
  }

}