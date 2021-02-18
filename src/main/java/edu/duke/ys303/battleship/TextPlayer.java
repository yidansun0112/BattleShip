package edu.duke.ys303.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
   * Print out prompt and let player to put ships on the board.
   *
   * @throws IOException when placement is not valid.
   */
  public void doPlacementPhase() throws IOException {
    BoardTextView view = new BoardTextView(theBoard);
    out.println(view.displayMyOwnBoard());
    out.println("Player " + name
        + ": you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical).  For example M4H would place a ship horizontally starting at M4 and going to the right.  You have\n");
    out.println("2 \"Submarines\" ships that are 1x2");
    out.println("3 \"Destroyers\" ships that are 1x3");
    out.println("3 \"Battleships\" ships that are 1x4");
    out.println("2 \"Carriers\" ships that are 1x6");
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
    if (s == null) {
      out.println("You missed!");
    } else {
      out.println("You hit a " + s.getName() + "!");
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
      oldShip.setMove();
      moveTimes--;
    } else {
      out.println(str);
      throw new IllegalArgumentException("This placement is invalid");
    }
  }

  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String myHeader, String enemyHeader)
      throws IOException {
    String text = view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader);
    out.print(text);
    out.println("Possible actions for Player A:\n");
    out.println("F Fire at a square");
    out.println("M Move a ship to another square (" + moveTimes + " remaining)");
    out.println("S Sonar scan (" + scanTimes + " remaining)\n");
    out.println("Player A, what would you like to do?");
    try {
      readChoice(enemyBoard, enemyView, myHeader, enemyHeader);
    } catch (IllegalArgumentException e) {
      out.println("Exception thrown" + e);
      out.println("Please make your choice again.");
      playOneTurn(enemyBoard, enemyView, myHeader, enemyHeader);
    }
  }

  public void readChoice(Board<Character> enemyBoard, BoardTextView enemyView, String myHeader, String enemyHeader)
      throws IOException {
    String s = inputReader.readLine();
    if (s == "F") {
      playOneFire(enemyBoard);
    } else if (s == "M") {
      if (moveTimes == 0) {
        throw new IllegalArgumentException("You don't have any move leaving!");
      }
      playOneMove();
    } else if (s == "S") {
      if (scanTimes == 0) {
        throw new IllegalArgumentException("You don't have any sonar scan leaving!");
      }
      playOneScan();
    }
  }

  public void playOneScan() {

  }

  /**
   * Declare this player win
   */
  public void declareWinner() {
    out.print("Player " + name + " win the game!\n");
  }
}
