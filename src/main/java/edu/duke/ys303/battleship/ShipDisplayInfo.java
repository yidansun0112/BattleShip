package edu.duke.ys303.battleship;

/**
 * Interface of basic ship display info
 *
 * @param T Character
 */
public interface ShipDisplayInfo<T> {

  /**
   * Return a char info of this coordinate based on whether hit or not.
   *
   * @param Coordinate to check with
   * @param boolean indicates hit or not.
   * @return type T info
   */
  public T getInfo(boolean hit);

}
