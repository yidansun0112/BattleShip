package edu.duke.ys303.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.function.Function;

/**
 * This class handles move of a computer player.
 */
public class HumanTextPlayer extends TextPlayer {

  /**
   * Constructor for HumanTextPlayer. Pass parameters to parent class.
   * 
   * @param String           name of this Player
   * @param Board<Character> theBoard to play on
   * @param BufferedReader   for input
   * @param PrintStream      for out
   * @param V2ShipFactory    to build ships
   */
  public HumanTextPlayer(String name, Board<Character> theBoard, BufferedReader inputReader, PrintStream out,
      V2ShipFactory factory) {
    super(name, theBoard, inputReader, out, factory);
  }

  /**
   * Print a prompt, read a string and generate a placement.
   * 
   * @param String prompt
   * @return Placement
   * @throws EOPException when input is null
   */
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException();
    }
    return new Placement(s);
  }

  /**
   * Print a prompt, read a string and generate a coordinate.
   * 
   * @param String prompt
   * @return Coordinate
   * @throws EOPException             when input is null
   * @throws IllegalArgumentException when coordinate is go off the board.
   */
  public Coordinate readCoordinate(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException();
    }
    Coordinate c = new Coordinate(s);
    if (c.getRow() > theBoard.getHeight() || c.getColumn() > theBoard.getWidth()) {
      throw new IllegalArgumentException("Coordinate goes off the board!");
    }
    return c;
  }

  /**
   * Do one placement on this Board.
   * 
   * @param String                              name of the ship to put
   * @param Function<Placement,Ship<Character>> function to create a ship.
   */
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    try {
      Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
      Ship<Character> s = createFn.apply(p);
      String str = theBoard.tryAddShip(s);
      if (str == null) {
        out.print(view.displayMyOwnBoard());
      } else {
        out.println(str);
        out.println("Please do that again.");
        doOnePlacement(shipName, createFn);
      }
    } catch (IllegalArgumentException e) {
      out.println("Exception thrown:" + e);
      out.println("Please do that aganin.");
      doOnePlacement(shipName, createFn);
    }
  }

  /**
   * Add ships on the board.
   * 
   * Print prompt. Get shipname from shipsToPlaces, then add this ship on the
   * board.
   */
  public void doPlacementPhase() throws IOException {
    BoardTextView view = new BoardTextView(theBoard);
    out.println(view.displayMyOwnBoard());
    out.println("Player " + name
        + ": you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical).  For example M4H would place a ship horizontally starting at M4 and going to the right.  You have\n");
    out.println("2 \"Submarines\" ships that are 1x2");
    out.println("3 \"Destroyers\" ships that are 1x3");
    out.println("3 \"Battleships\" ships that are 1x1+1x3");
    out.println("2 \"Carriers\" ships that are 1x3+1x3");
    for (String shipName : shipsToPlaces) {
      doOnePlacement(shipName, shipCreationFns.get(shipName));
    }
  }

  /**
   * Play one fire move at the enemyBoard.
   * 
   * @param Board<Character> enemyBoard If miss, print "You missed!" If hit, print
   *                         "You hit a xxship!
   */
  public void playOneFire(Board<Character> enemyBoard) throws IOException {
    Coordinate c = readCoordinate("Player " + name + " Please choose one place to fire at.");
    Ship<Character> s = enemyBoard.fireAt(c);
    if (s == null) {
      out.println("You missed!");
    } else {
      out.println("You hit a " + s.getName() + "!");
    }
  }

  /**
   * Play one movement.
   * 
   * Read a coordinate, then find a ship that occupies this coordinate and move
   * this ship.
   * 
   * @throw IllegalArgumentException when the given coordinate relates to no ship.
   */
  public void playOneMove() throws IOException {
    Coordinate c = readCoordinate(
        "Player " + name + " Please choose any place which is a part of the ship you want to move.");
    Ship<Character> s = theBoard.findShip(c);
    if (s == null) {
      throw new IllegalArgumentException("This coordinate is not part of any ship on the board.");
    }
    moveShip(s);
  }

  /**
   * Move the given ship.
   * 
   * Read a placement to move this ship. Transfer hit records between two ships.
   * 
   * @param Ship<Character> to move
   * @throw IllegalArgumentException when this placement is invalid, ex.goes off
   *        the board, collision...
   */
  public void moveShip(Ship<Character> oldShip) throws IOException {
    Placement p = readPlacement("Player " + name + " where do you want this " + oldShip.getName() + " move to?");
    Function<Placement, Ship<Character>> createFn = shipCreationFns.get(oldShip.getName());
    Ship<Character> newShip = createFn.apply(p);
    newShip.transferHit(oldShip);
    String str = theBoard.tryAddShip(newShip);
    if (str == null) {
      theBoard.removeShip(oldShip);
      updateMoveTimes();
    } else {
      out.println(str);
      throw new IllegalArgumentException("This placement is invalid");
    }
  }

  /**
   * Play one scan on enemyBoard.
   * 
   * @param Board<Character> enemyBoard to scan
   */
  public void playOneScan(Board<Character> enemyBoard) throws IOException {
    Coordinate coord = readCoordinate("Player " + name + " please choose one center coordinate to sonar scan!");
    HashMap<Character, Integer> count = shipCount(coord, enemyBoard);
    printResult(count);
    updateScanTimes();
  }

  /**
   * Count ship squares on the area decided by the coordinate
   * 
   * @param Coordinate
   * @param Board<Character> enemyBoard to scan
   * @return HashMap<Character,Integer>
   */
  public HashMap<Character, Integer> shipCount(Coordinate coord, Board<Character> enemyBoard) {
    int r = coord.getRow();
    int c = coord.getColumn();
    HashMap<Character, Integer> count = new HashMap<Character, Integer>();
    for (int i = -3; i <= 3; i++) {
      for (int j = -3; j <= 3; j++) {
        if (j - i <= 3 && j - i >= -3 && j + i <= 3 && j + i >= -3) {
          int row = r + j;
          int col = c + i;
          if (row >= 0 && row <= 19 && col >= 0 && col <= 9) {
            Coordinate toCheck = new Coordinate(row, col);
            Character chr = enemyBoard.whatIsAtForSelf(toCheck);
            if (chr != null && chr != '*') {
              count.put(chr, count.getOrDefault(chr, 0) + 1);
            }
          }
        }
      }
    }
    return count;
  }

  /**
   * print the result of scan. Go through the map, get number of each ship squares
   * 
   * @param HashMap<Character,Integer> of ships
   */
  private void printResult(HashMap<Character, Integer> shipCount) {
    out.println("Submarines occupy " + shipCount.getOrDefault('s', 0) + " squares");
    out.println("Destroyers occupy " + shipCount.getOrDefault('d', 0) + " squares");
    out.println("Battleships occupy " + shipCount.getOrDefault('b', 0) + " squares");
    out.println("Carriers occupy " + shipCount.getOrDefault('c', 0) + " squares");
  }

  /**
   * Play one turn for human player. May choose to fire, move or scan.
   * 
   * @param Board<Character> to play on
   * @param BoardTextView    of enemyBoard
   * @param String           my header of the board
   * @param String           enemy header of the board
   */
  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String myHeader, String enemyHeader)
      throws IOException {
    String text = view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader);
    out.print(text);
    try {
      if (getMoveTimes() == 0 && getScanTimes() == 0) {
        playOneFire(enemyBoard);
      } else {
        out.println("Possible actions for Player " + name + ":\n");
        out.println("F Fire at a square");
        out.println("M Move a ship to another square (" + getMoveTimes() + " remaining)");
        out.println("S Sonar scan (" + getScanTimes() + " remaining)\n");
        out.println("Player " + name + ", what would you like to do?");
        readChoice(enemyBoard, enemyView, myHeader, enemyHeader);
      }
    } catch (IllegalArgumentException e) {
      out.println("Exception thrown" + e);
      out.println("Please make your choice again.");
      playOneTurn(enemyBoard, enemyView, myHeader, enemyHeader);
    }
  }

  /**
   * Read a choice and act based on this choice.
   * 
   * @param Board<Character> to play on
   * @param BoardTextView    of enemyBoard
   * @param String           my header of the board
   * @param String           enemy header of the board
   * @return char
   */
  public void readChoice(Board<Character> enemyBoard, BoardTextView enemyView, String myHeader, String enemyHeader)
      throws IOException {
    String s = inputReader.readLine();
    if (s.length() != 1) {
      throw new IllegalArgumentException("Choice should only be one letter");
    }
    char c = s.charAt(0);
    if (c == 'F' || c == 'f') {
      playOneFire(enemyBoard);
    } else if (c == 'M' || c == 'm') {
      if (getMoveTimes() == 0) {
        throw new IllegalArgumentException("You don't have any move leaving!");
      }
      playOneMove();
    } else if (c == 'S' || c == 's') {
      if (getScanTimes() == 0) {
        throw new IllegalArgumentException("You don't have any sonar scan leaving!");
      }
      playOneScan(enemyBoard);
    } else {
      throw new IllegalArgumentException("Wrong Choice, You should choose between F, M and S");
    }
  }

}
