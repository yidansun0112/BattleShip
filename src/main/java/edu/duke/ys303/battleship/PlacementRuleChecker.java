package edu.duke.ys303.battleship;

/**
 * Abstrct class to help check rules.Use tail recursions here to check one by
 * one.
 */
public abstract class PlacementRuleChecker<T> {

  /**
   * next rule to check.
   */
  private final PlacementRuleChecker<T> next;

  /**
   * Constructor for PlacementRuleChecker.
   *
   * @param PlacementRuleChecker<T> next rule to check.
   */
  public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
  }

  /**
   * Abstract method to check rule.Defined in children classes.
   *
   * @param Ship<T>  the ship to check with.
   * @param Board<T> the board to check with.
   */
  protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);

  /**
   * Tail recursion for chekcing all rules.
   *
   * @param Ship<T>  to check with.
   * @param Board<T> to check with.
   * @return true if rule is obeyed, false otherwise.
   */
  public String checkPlacement(Ship<T> theShip, Board<T> theBoard) {
    String placementProblem = checkMyRule(theShip, theBoard);
    if (placementProblem != null) {
      return placementProblem;
    }
    // other wise, ask the rest of the chain.
    if (next != null) {
      return next.checkPlacement(theShip, theBoard);
    }
    // if there are no more rules, then the placement
    return null;
  }

}
