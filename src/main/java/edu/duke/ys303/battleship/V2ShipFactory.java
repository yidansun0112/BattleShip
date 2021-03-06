package edu.duke.ys303.battleship;

/**
 * This class handles a ship factory for version 2 of this game.
 *
 * @param T Character
 */
public class V2ShipFactory implements AbstractShipFactory<Character> {

  /**
   * Helper method to create a ship.
   *
   * @param Placement of where and orientation to place the ship.
   * @param int       width of the ship.
   * @param int       height of the ship.
   * @param char      indicates how this ship is showed on board.
   * @param String    this ship's name.
   * @return a ship.
   */
  protected Ship<Character> createRectangleShip(Placement where, int w, int h, char letter, String name) {
    char ort = where.getOrientation();
    if (ort != 'V' && ort != 'H') {
      throw new IllegalArgumentException(
          "Wrong orientation of this Placement! Should be 'V' or 'H', but is" + Character.toString(ort));
    }
    if (ort == 'V') {
      return new RectangleShip<Character>(name, where.getWhere(), h, w, letter, '*');
    } else {
      return new RectangleShip<Character>(name, where.getWhere(), w, h, letter, '*');
    }
  }

  /**
   * Make a submarine.
   * 
   * @param Placement specifies the location and orientation of the ship to make
   * @return the Ship created for the submarine.
   */
  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    return createRectangleShip(where, 1, 2, 's', "Submarine");
  }

  /**
   * Make a destroyer.
   * 
   * @param Placement specifies the location and orientation of the ship to make
   * @return Ship created for the destroyer.
   */

  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    return createRectangleShip(where, 1, 3, 'd', "Destroyer");
  }

  /**
   * Helper method to create a ship.
   *
   * @param Placement of where and orientation to place the ship.
   * @param char      indicates how this ship is showed on board.
   * @param String    this ship's name.
   * @return a ship.
   */
  protected Ship<Character> createShip(Placement where, char letter, String name) {
    char ort = where.getOrientation();
    if (ort != 'U' && ort != 'R' && ort != 'D' && ort != 'L') {
      throw new IllegalArgumentException(
          "Wrong orientation of this Placement! Should be 'U' or 'R' or 'D' or 'L', but is" + Character.toString(ort));
    }
    if (name == "Battleship") {
      return new BattleShip<Character>(name, where, letter, '*');
    } else {
      return new CarrierShip<Character>(name, where, letter, '*');
    }
  }

  /**
   * Make a battleship.
   * 
   * @param Placement specifies the location and orientation of the ship to make
   * @return the Ship created for the battleship.
   */
  @Override
  public Ship<Character> makeBattleship(Placement where) {
    return createShip(where, 'b', "Battleship");
  }

  /**
   * Make a carrier.
   * 
   * @param Placement specifies the location and orientation of the ship to make
   * @return the Ship created for the carrier.
   */
  @Override
  public Ship<Character> makeCarrier(Placement where) {
    return createShip(where, 'c', "Carrier");
  }

}
