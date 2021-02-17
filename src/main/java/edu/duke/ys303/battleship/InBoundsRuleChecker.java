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
   * @return String null if the rule is obeyed, a string specifies exact problem
   *         otherwise.
   */
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    int width = theBoard.getWidth();
    int height = theBoard.getHeight();
    Iterable<Coordinate> s = theShip.getCoordinates();
    for (Coordinate c : s) {
      if (c.getRow() < 0) {
        return "That placement is invalid: the ship goes off the top of the board.";
      }
      if (c.getRow() >= height) {
        return "That placement is invalid: the ship goes off the bottom of the board.";
      }
      if (c.getColumn() < 0) {
        return "That placement is invalid: the ship goes off the left of the board.";
      }
      if (c.getColumn() >= width) {
        return "That placement is invalid: the ship goes off the right of the board.";
      }
    }
    return null;
  }

  /**
   * Constructor for InBoundsRuleChecker. Pass paramter to parent class via
   * super().
   *
   * @param PlacementRuleChecker<T> passed to parent class constructor.
   */
  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }
}
