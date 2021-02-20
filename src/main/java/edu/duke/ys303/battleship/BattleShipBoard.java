package edu.duke.ys303.battleship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

/**
 * This class handles a battleship board, which implements from basic Board<T>
 *
 * @param T Character.
 */
public class BattleShipBoard<T> implements Board<T> {
  /** width of the board. */
  private final int width;
  /** height of the board. */
  private final int height;
  /** ArrayList of ships in this board. */
  final ArrayList<Ship<T>> myShips;
  /** rule checker for this board. */
  private final PlacementRuleChecker<T> placementChecker;
  /** HashSet of misses for this Board. */
  HashSet<Coordinate> enemyMisses;
  /** HashSet of hits for this Board. */
  HashMap<Coordinate, T> enemyHits;
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
   * @param T                       miss information to display when miss.
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
    enemyHits = new HashMap<Coordinate, T>();
    this.missInfo = missInfo;
  }

  /**
   * Convenience constructor for BattleShipBoard. Pass combined two ruleCheckers
   * to placementCheck.
   *
   * @param int the width of the newly constructed board.
   * @param int the height of the newly constructed board.
   * @param T   miss information to display when miss.
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */
  public BattleShipBoard(int w, int h, T missInfo) {
    this(w, h, new InBoundsRuleChecker<T>(new NoCollisionRuleChecker<T>(null)), missInfo);
  }

  /**
   * Remove the given ship from this board.
   * 
   * @param Ship<T> to remove
   */
  @Override
  public void removeShip(Ship<T> s) {
    myShips.remove(s);
  }

  /**
   * Add a ship into ArrayList myShips when ruleChecker returns null.
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
   * Takes a Coordinate, and see if any ship occupies that coordinate. Self view.
   *
   * @param Cooridinate to check.
   * @return T SimpleDisplayInfo of the ship, if one is found. NULL, when no one
   *         is found.
   */
  @Override
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  /**
   * Takes a Coordinate, and see if any ship occupies that coordinate. Enemy view.
   *
   * @param Cooridinate to check.
   * @return T SimpleDisplayInfo of the ship, if one is found. NULL, when no one
   *         is found.
   */
  @Override
  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }

  /**
   * Based on whether self or not, give what is at this coordinate.
   *
   * @param Coordinate to check with.
   * @param boolean    indicate whether self or not.
   * @return T info of this coordinate.
   */
  protected T whatIsAt(Coordinate where, boolean isSelf) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where, isSelf, enemyHits.containsKey(where));
      }
    }
    if (!isSelf && enemyMisses.contains(where)) {
      return missInfo;
    }
    if (!isSelf && enemyHits.containsKey(where)) {
      return enemyHits.get(where);
    }
    return null;
  }

  /**
   * Search for any ship that occupies Coordinate c. If one is found, that Ship is
   * returned. Else,return null.
   *
   * @param Coordinate to attack on.
   * @return Ship<T> if one ship is found, null otherwise.
   */
  @Override
  public Ship<T> findShip(Coordinate c) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(c)) {
        return s;
      }
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
    Ship<T> s = findShip(c);
    if (s != null) {
      s.recordHitAt(c);
      enemyHits.put(c, s.getData());
      enemyMisses.remove(c);
      return s;
    }
    enemyMisses.add(c);
    enemyHits.remove(c);
    return null;
  }

  /**
   * Check whether all ships on the Board are sunk
   *
   * @return boolean true indicates all sunk, false otherwise.
   */
  @Override
  public boolean allSunk() {
    for (Ship<T> s : myShips) {
      if (!s.isSunk()) {
        return false;
      }
    }
    return true;
  }

}
