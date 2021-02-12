package edu.duke.ys303.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of a Board (i.e., converting it to a
 * string to show to the user). It supports two ways to display the Board: one
 * for the player's own board, and one for the enemy's board.
 */
public class BoardTextView {

  /**
   * The Board to display
   */
  private final Board<Character> toDisplay;

  /**
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display
   * @throws IllegalArgumentException if the board is larger than 10x26.
   */
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }

  /**
   * This makes the header line, e.g. 0|1|2|3|4\n
   * 
   * @return the String that is the header line for the given board
   */
  String makeHeader() {
    StringBuilder ans = new StringBuilder("  "); // README shows two spaces at
    String sep = ""; // start with nothing to separate, then switch to | to separate
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }

  /**
   * This display the Text Board.
   *
   * @param Function<Coordinate,Character> applied to display based on self or
   *                                       enemy.
   * @return String of the Board text.
   */
  public String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
    String display = this.makeHeader();
    for (int row = 0; row < toDisplay.getHeight(); row++) {
      display += (Character.toString('A' + row)) + " ";
      for (int column = 0; column < toDisplay.getWidth(); column++) {
        Coordinate c = new Coordinate(row, column);
        Character cr = getSquareFn.apply(c);
        if (cr != null) {
          display += cr;
        } else {
          display += " ";
        }
        if (column < toDisplay.getWidth() - 1) {
          display += "|";
        }
      }
      display += (" " + Character.toString('A' + row) + "\n");
    }
    display += this.makeHeader();
    return display;
  }

  /**
   * Display own board.
   *
   * @return String text display of the Board, self view.
   */
  public String displayMyOwnBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForSelf(c));
  }

  /**
   * Display enemy board.
   *
   * @return String text display of the Board, enemy view.
   */
  public String displayEnemyBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForEnemy(c));
  }

  /**
   * Display two player's board side by side.
   *
   * @param BoardTextView another player's board view
   * @param String        myHeader
   * @param String        enemyHeader.
   * @return String text view of two board.
   */
  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
    String own = this.displayMyOwnBoard();
    String enemy = enemyView.displayEnemyBoard();
    String[] ownLines = own.split("\n");
    String[] enemyLines = enemy.split("\n");
    StringBuilder ss = new StringBuilder();
    int w = toDisplay.getWidth();
    int h = toDisplay.getHeight();
    String space = "                                ";
    ss.append(space, 0, 5);
    ss.append(myHeader);
    ss.append(space, 0, 2 * w + 7);
    ss.append(enemyHeader);
    ss.append("\n");
    for (int i = 0; i < h + 2; i++) {
      ss.append(ownLines[i]);
      if (i == 0 || i == h + 1) {
        ss.append(space, 0, 18);
      } else {
        ss.append(space, 0, 16);
      }
      ss.append(enemyLines[i]);
      ss.append("\n");
    }
    return ss.toString();
  }
}
