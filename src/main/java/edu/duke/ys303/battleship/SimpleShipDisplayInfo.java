package edu.duke.ys303.battleship;

/**
 * This class handles simple ship display. Based on whether this coordinate has
 * been hit or not , display the 's'(not hit) or '*' (hit)
 */
public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {

  /**
   * ship data
   */
  private T myData;

  /**
   * hit data
   */
  private T onHit;

  /**
   * Constructor of SimpleShipDisplayInfo
   *
   * @param T for myData
   * @param T for onHit
   */
  public SimpleShipDisplayInfo(T d, T h) {
    myData = d;
    onHit = h;
  }

  /**
   * Override method of getInfo
   *
   * @param Coordinate to get info from
   * @param boolean    indicate whether hit or not
   */
  @Override
  public T getInfo(Coordinate where, boolean hit) {
    if (hit) {
      return onHit;
    }
    return myData;
  }

}
