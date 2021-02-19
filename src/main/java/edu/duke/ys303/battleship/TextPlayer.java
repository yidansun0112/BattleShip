package edu.duke.ys303.battleship;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;
import java.io.IOException;

/**
 * This class constructs TextPlayer that could place ships on the board and do
 * attack.
 */
public abstract class TextPlayer {
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
   * Check whether this player is lose by checking if all ships are sunk
   *
   * @return true if lose, false otherwise
   */
  public boolean isLose() {
    return theBoard.allSunk();
  }

  public void updateScanTimes(){
    scanTimes--;
  }

  public void updateMoveTimes(){
    moveTimes--;
  }

  public int getMoveTimes(){
    return moveTimes;
  }

  public int getScanTimes() {
    return scanTimes;
  } 

  /**
   * Declare this player win
   */
  public void declareWinner() {
    out.println("Player " + name + " win the game!");
  }

  public void doPlacementPhase() throws IOException{

  }

  public void playOneTurn(Board<Character> theBoard,BoardTextView view, String myHeader, String enemyHeader) throws IOException{

  }
}
