package edu.duke.ys303.battleship;

import java.util.ArrayList;

public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;

  /**
   * Get width of the board.
   *
   * @return width of the board.
   */
  @Override
  public int getWidth() {
    return width;
  }

  /**
   * Get height of the board.
   * 
   * @return height of the board.
   */
  @Override
  public int getHeight() {
    return height;
  }

  /**
   * Constructs a BattleShipBoard with the specified width and height
   * 
   * @param int                     the width of the newly constructed board.
   * @param int                     the height of the newly constructed board.
   * @param PlacementRuleChecker<T> rules to check with.
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */

  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> placementChecker) {
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
    myShips = new ArrayList<Ship<T>>();
    this.placementChecker = placementChecker;
  }

  /**
   * Convenience constructor for BattleShipBoard. Pass in null to RuleChecker.
   * 
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */
  public BattleShipBoard(int w, int h) {
    this(w, h, new InBoundsRuleChecker<T>(null));
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
   * Takes a Coordinate, and see if any ship occupies that coordinate.
   *
   * @param Cooridinate to check.
   * @return SimpleDisplayInfo of the ship, if one is found. NULL, when no one is
   *         found.
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
