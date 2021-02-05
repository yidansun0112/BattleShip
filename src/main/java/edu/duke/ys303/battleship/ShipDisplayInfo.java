package edu.duke.ys303.battleship;

/**
 * Interface of basic ship display info
 */
public interface ShipDisplayInfo<T> {

  /**
   * Basic getInfo method
   *
   * @return type T info
   */
  public T getInfo(Coordinate where, boolean hit);
}
