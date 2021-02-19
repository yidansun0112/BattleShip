package edu.duke.ys303.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Function;

/**
 * This class constructs TextPlayer that could place ships on the board and do
 * attack.
 */
public class TextPlayer {
  /** the board to play with */
  final Board<Character> theBoard;
  /** the view to display */
  final BoardTextView view;
  /** buffer to read input from */
  final BufferedReader inputReader;
  /** stream to print out the information */
  final PrintStream out;
  /** ship factory to add ship on */
  final AbstractShipFactory<Character> shipFactory;
  /** name of this player */
  final String name;
  /** an arraylist of ships name to place */
  final ArrayList<String> shipsToPlaces;
  /** HashMap to handle ship creation functions */
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
  /** Times left for move. */
  private int moveTimes;
  /** Times left for scan. */
  private int scanTimes;
  /** indicate whether this player is computer ot not */
  private boolean isComputer;
  /** Random object to generate numbers */
  private Random rdm;

  /**
   * Constructor for TextPlayer
   *
   * @param String         for player's name;
   * @param Board          to play with
   * @param BufferedReader for input
   * @param PrintSteeam    for output
   * @param V1ShipFactory  to initialize shipFactory
   */
  public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputReader, PrintStream out,
      V2ShipFactory factory) {
    this.name = name;
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = inputReader;
    this.out = out;
    this.shipFactory = factory;
    shipsToPlaces = new ArrayList<String>();
    shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
    setupShipCreationMap();
    setupShipCreationList();
    moveTimes = 3;
    scanTimes = 3;
    isComputer = false;
    rdm = new Random();
  }

  public int getMoveTimes() {
    return moveTimes;
  }

  public int getScanTimes() {
    return scanTimes;
  }

  /**
   * Set up shipCreationMap. Put ship creation functions into a map.
   */
  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
  }

  /**
   * Set up ships list.
   */
  protected void setupShipCreationList() {
    shipsToPlaces.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlaces.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlaces.addAll(Collections.nCopies(3, "Battleship"));
    shipsToPlaces.addAll(Collections.nCopies(2, "Carrier"));
  }

  /**
   * Give out a prompt, and receive a placement from input.
   *
   * @param String prompt to ask for input.
   * @return a Placement.
   * @throws EOFException             when input is null.
   * @throws IllegalArgumentException when string for placement is not valid
   */
  public Placement readPlacement(String prompt) throws IllegalArgumentException, IOException {
    if (isComputer) {
      int row = rdm.nextInt(theBoard.getHeight());
      int col = rdm.nextInt(theBoard.getWidth());
      String oriChoice = "VHURDL";
      char ori = oriChoice.charAt(rdm.nextInt(6));
      return new Placement(new Coordinate(row, col), ori);
    }
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException();
    }
    return new Placement(s);
  }

  /**
   * Give out a prompt, and receive a placement from input.
   *
   * @param String prompt to ask for input.
   * @return a Coordinate
   * @throws EOFException             when input is null.
   * @throws IllegalArgumentException when string for Coordinate is not valid
   */
  public Coordinate readCoordinate(String prompt) throws IllegalArgumentException, IOException {
    Coordinate c;
    if (isComputer) {
      int row = rdm.nextInt(theBoard.getHeight());
      int col = rdm.nextInt(theBoard.getWidth());
      c = new Coordinate(row, col);
    } else {
      out.println(prompt);
      String s = inputReader.readLine();
      if (s == null) {
        throw new EOFException();
      }
      c = new Coordinate(s);
    }
    if (c.getRow() > theBoard.getHeight() || c.getColumn() > theBoard.getWidth()) {
      throw new IllegalArgumentException("Coordinate goes off the board!");
    }
    return c;
  }

  /**
   * Take a string name, take a proper method and then put the ship on board.
   *
   * @param String              name of the ship.
   * @param Function<Placement, Ship<Character>> function to make ship.
   * @throws IllegalArgumentException when string for placement is not validc
   * @throws EOFException             when input is null.
   */
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn)
      throws IllegalArgumentException, IOException {
    try {
      Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
      Ship<Character> s = createFn.apply(p);
      String str = theBoard.tryAddShip(s);
      if (!isComputer) {
        if (str == null) {
          out.print(view.displayMyOwnBoard());
        } else {
          out.println(str);
          out.println("Please do that again.");
          doOnePlacement(shipName, createFn);
        }
      }
    } catch (IllegalArgumentException e) {
      if (!isComputer) {
        out.println("Exception thrown:" + e);
        out.println("Please do that aganin.");
      }
      doOnePlacement(shipName, createFn);
    }
  }

  /**
   * Print out prompt and let player to put ships on the board.
   *
   * @throws IOException when placement is not valid.
   */
  public void doPlacementPhase() throws IOException {
    if (!isComputer) {
      BoardTextView view = new BoardTextView(theBoard);
      out.println(view.displayMyOwnBoard());
      out.println("Player " + name
          + ": you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical).  For example M4H would place a ship horizontally starting at M4 and going to the right.  You have\n");
      out.println("2 \"Submarines\" ships that are 1x2");
      out.println("3 \"Destroyers\" ships that are 1x3");
      out.println("3 \"Battleships\" ships that are 1x1+1x3");
      out.println("2 \"Carriers\" ships that are 1x3+1x3");
    }
    for (String shipName : shipsToPlaces) {
      doOnePlacement(shipName, shipCreationFns.get(shipName));
    }
  }

  /**
   * Check whether this player is lose by checking if all ships are sunk
   *
   * @return true if lose, false otherwise
   */
  public boolean isLose() {
    return theBoard.allSunk();
  }

  /**
   * Play one attack on enemy's board.
   *
   * @param BoardTextView enemy's view
   * @param Board         enemy's board.
   * @param myHeader
   * @param enemyHeader
   */
  public void playOneFire(Board<Character> enemyBoard) throws IOException {
    Coordinate c = readCoordinate("Player " + name + " Please choose one place to fire at.");
    Ship<Character> s = enemyBoard.fireAt(c);
    if (!isComputer) {
      if (s == null) {
        out.println("You missed!");
      } else {
        out.println("You hit a " + s.getName() + "!");
      }
    } else {
      if (s == null) {
        out.println("Player " + name + " missed!");
      } else {
        out.println("Player " + name + " hit your " + s.getName() + " at " + c.toString() + "!");
      }
    }
  }

  public void playOneMove() throws IOException {
    Coordinate c = readCoordinate(
        "Player " + name + " Please choose any place which is a part of the ship you want to move.");
    Ship<Character> s = theBoard.findShip(c);
    if (s == null) {
      throw new IllegalArgumentException("This coordinate is not part of any ship on the board.");
    }
    moveShip(s);
  }

  public void moveShip(Ship<Character> oldShip) throws IOException {
    Placement p = readPlacement("Player " + name + " where do you want this " + oldShip.getName() + " move to?");
    Function<Placement, Ship<Character>> createFn = shipCreationFns.get(oldShip.getName());
    Ship<Character> newShip = createFn.apply(p);
    newShip.transferHit(oldShip);
    String str = theBoard.tryAddShip(newShip);
    if (str == null) {
      theBoard.removeShip(oldShip);
      moveTimes--;
      if (isComputer) {
        out.println("Player " + name + " used a special action!");
      }
    } else {
      if (!isComputer) {
        out.println(str);
      }
      throw new IllegalArgumentException("This placement is invalid");
    }
  }

  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String myHeader, String enemyHeader)
      throws IOException {
    if (!isComputer) {
      String text = view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader);
      out.print(text);
    }
    try {
      if (moveTimes == 0 && scanTimes == 0) {
        playOneFire(enemyBoard);
      } else {
        if (!isComputer) {
          out.println("Possible actions for Player " + name + ":\n");
          out.println("F Fire at a square");
          out.println("M Move a ship to another square (" + moveTimes + " remaining)");
          out.println("S Sonar scan (" + scanTimes + " remaining)\n");
          out.println("Player " + name + ", what would you like to do?");
        }
        readChoice(enemyBoard, enemyView, myHeader, enemyHeader);
      }
    } catch (IllegalArgumentException e) {
      if (!isComputer) {
        out.println("Exception thrown" + e);
        out.println("Please make your choice again.");
      }
      playOneTurn(enemyBoard, enemyView, myHeader, enemyHeader);
    }
  }

  public void readChoice(Board<Character> enemyBoard, BoardTextView enemyView, String myHeader, String enemyHeader)
      throws IOException {
    char c = ' ';
    if (!isComputer) {
      String s = inputReader.readLine();
      if (s.length() != 1) {
        throw new IllegalArgumentException("Choice should only be one letter");
      }
      c = s.charAt(0);
    } else {
      String choice = "FMS";
      c = choice.charAt(rdm.nextInt(3));
    }
    if (c == 'F' || c == 'f') {
      playOneFire(enemyBoard);
    } else if (c == 'M' || c == 'm') {
      if (moveTimes == 0) {
        throw new IllegalArgumentException("You don't have any move leaving!");
      }
      playOneMove();
    } else if (c == 'S' || c == 's') {
      if (scanTimes == 0) {
        throw new IllegalArgumentException("You don't have any sonar scan leaving!");
      }
      playOneScan(enemyBoard);
    } else {
      throw new IllegalArgumentException("Wrong Choice, You should choose between F, M and S");
    }
  }

  public void decideHumanComputer() throws IOException {
    out.println("Do you want Player " + name + " a human player or to be played by the computer?");
    out.println("H for human, C for computer");
    out.println("Please make your choice:");
    try {
      String s = inputReader.readLine();
      if (s.length() != 1) {
        throw new IllegalArgumentException("Choice should only be one letter");
      }
      char c = s.charAt(0);
      if (c == 'H' || c == 'h') {
        isComputer = false;
      } else if (c == 'C' || c == 'c') {
        isComputer = true;
      } else {
        throw new IllegalArgumentException("Choice should be H or C!");
      }
    } catch (IllegalArgumentException e) {
      out.println("Exception thrown:" + e);
      out.println("Please do that again!");
      decideHumanComputer();
    }
  }

  public void playOneScan(Board<Character> enemyBoard) throws IOException {
    if (isComputer) {
      out.println("Player " + name + " used a special action!");
      scanTimes--;
      return;
    }
    Coordinate coord = readCoordinate("Player " + name + " please choose one center coordinate to sonar scan!");
    int r = coord.getRow();
    int c = coord.getColumn();
    HashMap<Character, Integer> shipCount = new HashMap<Character, Integer>();
    for (int i = -3; i <= 3; i++) {
      for (int j = -3; j <= 3; j++) {
        if (j - i <= 3 && j - i >= -3 && j + i <= 3 && j + i >= -3) {
          int row = r + j;
          int col = c + i;
          if (row >= 0 && row <= 19 && col >= 0 && col <= 9) {
            Coordinate toCheck = new Coordinate(row, col);
            Character chr = enemyBoard.whatIsAtForSelf(toCheck);
            if (chr != null && chr != '*') {
              shipCount.put(chr, shipCount.getOrDefault(chr, 0) + 1);
            }
          }
        }
      }
    }
    printResult(shipCount);
    scanTimes--;
  }

  public void printResult(HashMap<Character, Integer> shipCount) {
    out.println("Submarines occupy " + shipCount.getOrDefault('s', 0) + " squares");
    out.println("Destroyers occupy " + shipCount.getOrDefault('d', 0) + " squares");
    out.println("Battleships occupy " + shipCount.getOrDefault('b', 0) + " squares");
    out.println("Carriers occupy " + shipCount.getOrDefault('c', 0) + " squares");
  }

  /**
   * Declare this player win
   */
  public void declareWinner() {
    out.println("Player " + name + " win the game!");
  }
}
