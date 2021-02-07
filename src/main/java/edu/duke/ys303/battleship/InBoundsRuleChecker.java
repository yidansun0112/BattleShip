package edu.duke.ys303.battleship;

/**
 * This class extends from PlacementRuleChecker and handles bound check.
 */
public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  /**
   * Override method checkMyRule. The Rule here is whether extend bounds.
   *
   * @param Ship<T>  the ship to decide.
   * @param Board<T> the board to decide.
   * @return true if the rule is obeyed, false otherwise.class
   */
  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    int width = theBoard.getWidth();
    int height = theBoard.getHeight();
    Iterable<Coordinate> s = theShip.getCoordinates();
    for (Coordinate c : s) {
      if (c.getRow() < 0 || c.getRow() >= height) {
        return false;
      }
      if (c.getColumn() < 0 || c.getColumn() >= width) {
        return false;
      }
    }
    return true;
  }

  /**
   * Constructor for InBoundsRuleChecker.
   *
   * @param PlacementRuleChecker<T> passed to parent class constructor.
   */
  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }
}
