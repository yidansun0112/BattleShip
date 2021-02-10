package edu.duke.ys303.battleship;



/**
 * This class extends from PlacementRuleChecker and handles no collision check.
 */
public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {

  /**
   * Override method checkMyRule. The Rule here is no collision occurs.
   *
   * @param Ship<T>  the ship to decide.
   * @param Board<T> the board to decide.
   * @return null when rule is obeyed, a string specifies exact problem otherwise.
   */
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> cs = theShip.getCoordinates();
    for (Coordinate c : cs) {
      if (theBoard.whatIsAt(c) != null) {
        return "That placement is invalid: the ship overlaps another ship.";
      }
    }
    return null;
  }

  /**
   * Constructor for NoCollisionRuleChecker.
   *
   * @param PlacementRuleChecker<T> passed to parent class constructor.
   */
  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }
}
