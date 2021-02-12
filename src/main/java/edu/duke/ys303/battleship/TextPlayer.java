package edu.duke.ys303.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class TextPlayer {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  final String name;
  final ArrayList<String> shipsToPlaces;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;

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
      V1ShipFactory factory) {
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
    return new Coordinate(s);
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
   * @param Board         enemy's board.
   * @param BoardTextView enemy's view
   * @param myHeaderc
   * @param enemyHeader
   */
  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String myHeader, String enemyHeader)
      throws IOException {
    String text = view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader);
    out.print(text);
    Coordinate c = readCoordinate("Player " + name + " Please choose one place to fire at.");
    Ship<Character> s = enemyBoard.fireAt(c);
    if (s == null) {
      out.println("You missed!");
    } else {
      out.println("You hit a " + s.getName() + "!");
    }
  }

  /**
   * Declare this player win
   */
  public void declareWinner() {
    out.println("Player " + name + " win the game!");
  }
}
