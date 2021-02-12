package edu.duke.ys303.battleship;

import java.util.ArrayList;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T> {
  /** width of the board. */
  private final int width;
  /** height of the board. */
  private final int height;
  /** ArrayList of ships in this board. */
  final ArrayList<Ship<T>> myShips;
  /** rule checker for this board. */
  private final PlacementRuleChecker<T> placementChecker;
  /** HashSet of misses of this Board. */
  HashSet<Coordinate> enemyMisses;
  /** missInfo to display for this board. */
  final T missInfo;

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

  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> placementChecker, T missInfo) {
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
    enemyMisses = new HashSet<Coordinate>();
    this.missInfo = missInfo;
  }

  /**
   * Convenience constructor for BattleShipBoard. Pass combined two ruleCheckers
   * to placementCheck.
   *
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */
  public BattleShipBoard(int w, int h, T missInfo) {
    this(w, h, new InBoundsRuleChecker<T>(new NoCollisionRuleChecker<T>(null)), missInfo);
  }

  /**
   * Add a ship into ArrayList myShips when ruleChecker returns true.
   *
   * @param Ship<T> the ship to add.
   * @return null if add successfully, a String specifies placement problem
   *         otherwise.
   */
  public String tryAddShip(Ship<T> toAdd) {
    String placementProblem = placementChecker.checkPlacement(toAdd, this);
    if (placementProblem == null) {
      myShips.add(toAdd);
      return null;
    }
    return placementProblem;
  }

  /**
   * Takes a Coordinate, and see if any ship occupies that coordinate.Self view.
   *
   * @param Cooridinate to check.
   * @return SimpleDisplayInfo of the ship, if one is found. NULL, when no one is
   *         found.
   */
  @Override
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  /**
   * Takes a Coordinate, and see if any ship occupies that coordinate. Enemy view.
   *
   * @param Cooridinate to check.
   * @return SimpleDisplayInfo of the ship, if one is found. NULL, when no one is
   *         found.
   */
  @Override
  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }

  /**
   * Based on where self or not, give what is at this coordinate.
   *
   * @param Coordinate to check with.
   * @param boolean    indicate whether self or not.
   * @return T info of this coordinate.
   */
  protected T whatIsAt(Coordinate where, boolean isSelf) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where, isSelf);
      }
    }
    if (!isSelf && enemyMisses.contains(where)) {
      return missInfo;
    }
    return null;
  }

  /**
   * Search for any ship that occupies Coordinate c. If one is found, that Ship is
   * "hit" by the attack and record. Else, add Coordinate c into enemyMisses Set.
   *
   * @param Coordinate to attack on.
   * @return Ship<T> if one ship is hit, null otherwise.
   */
  @Override
  public Ship<T> fireAt(Coordinate c) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(c)) {
        s.recordHitAt(c);
        return s;
      }
    }
    enemyMisses.add(c);
    return null;
  }
}
