package edu.duke.ys303.battleship;

import java.util.Random;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.function.Function;

/**
 * This class handles move of a computer player.
 */
public class ComputerTextPlayer extends TextPlayer {
  /** Random object to generate numbers */
  private Random rdm;

  /**
   * Constructor for ComputerTextPlayer
   * 
   * @param String name of this Player
   * @param Board<Character> theBoard to play on
   * @param BufferedReader for input
   * @param PrintStream for out
   * @param V2ShipFactory to build ships
   */
  public ComputerTextPlayer(String name, Board<Character> theBoard, BufferedReader inputReader, PrintStream out,
      V2ShipFactory factory) {
    this(name,theBoard,inputReader,out,factory,false);
  }

  /**
   * Constructor for ComputerTextPlayer. Pass parameters to parent class.
   * 
   * @param String name of this Player
   * @param Board<Character> theBoard to play on
   * @param BufferedReader for input
   * @param PrintStream for out
   * @param V2ShipFactory to build ships
   * @param boolean indicates whether use seed (for test) or not
   */
  public ComputerTextPlayer(String name, Board<Character> theBoard, BufferedReader inputReader, PrintStream out,
                            V2ShipFactory factory,boolean seed) {
    super(name, theBoard, inputReader, out, factory);
    if(seed){
      rdm=new Random(50);
    }
    else{
    rdm = new Random();
    }
  }

  /**
   * Randomly generate a placement.
   * 
   * @return Placement
   */
  public Placement readPlacement() {
    int row = rdm.nextInt(theBoard.getHeight());
    int col = rdm.nextInt(theBoard.getWidth());
    //String to get orientation from
    String oriChoice = "VHURDL";
    char ori = oriChoice.charAt(rdm.nextInt(6));
    return new Placement(new Coordinate(row, col), ori);
  }

  /**
   * Randomly generate a coordinate
   * 
   * @return Coordinate
   */
  public Coordinate readCoordinate() {
    int row = rdm.nextInt(theBoard.getHeight());
    int col = rdm.nextInt(theBoard.getWidth());
    Coordinate c = new Coordinate(row, col);
    return c;
  }

  /**
   * Do one placement on this Board.
   * 
   * @param String name of the ship to put
   * @param Function<Placement,Ship<Character>> function to create a ship.
   */
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) {
    try {
      Placement p = readPlacement();
      Ship<Character> s = createFn.apply(p);
      String str = theBoard.tryAddShip(s);
      if (str != null) {
        doOnePlacement(shipName, createFn);
      }
    } catch (IllegalArgumentException e) {
      doOnePlacement(shipName, createFn);
    }
  }

  /**
   * Add ships on the board.
   * 
   * Get shipname from shipsToPlaces, then add this ship on the board.
   */
  public void doPlacementPhase() {
    for (String shipName : shipsToPlaces) {
      doOnePlacement(shipName, shipCreationFns.get(shipName));
    }
  }

  /**
   * Play one fire move at the enemyBoard.
   * 
   * @param Board<Character> enemyBoard
   * If miss, print "Player xx missed!"
   * If hit, print "Player xx hit your xxship at coordinate!"
   */
  public void playOneFire(Board<Character> enemyBoard) {
    Coordinate c = readCoordinate();
    Ship<Character> s = enemyBoard.fireAt(c);
    if (s == null) {
      out.println("Player " + name + " missed!");
    } else {
      out.println("Player " + name + " hit your " + s.getName() + " at " + c.toString() + "!");
    }
  }

  /**
   * Play one movement.
   * 
   * Generate a coordinate, then find a ship that occupies this coordinate and move this ship.
   * @throw IllegalArgumentException when the given coordinate relates to no ship.
   */
  public void playOneMove() {
    Coordinate c = readCoordinate();
    Ship<Character> s = theBoard.findShip(c);
    if (s == null) {
      throw new IllegalArgumentException("This coordinate is not part of any ship on the board.");
    }
    moveShip(s);
  }

  /**
   * Move the given ship.
   * 
   * Generate a placement to move this ship.
   * Transfer hit records between two ships.
   * 
   * @param Ship<Character> to move
   * @throw IllegalArgumentException when this placement is invalid, ex.goes off the board, collision...
   */
  public void moveShip(Ship<Character> oldShip) {
    Placement p = readPlacement();
    Function<Placement, Ship<Character>> createFn = shipCreationFns.get(oldShip.getName());
    Ship<Character> newShip = createFn.apply(p);
    newShip.transferHit(oldShip);
    String str = theBoard.tryAddShip(newShip);
    if (str == null) {
      theBoard.removeShip(oldShip);
      updateMoveTimes();
    } else {
      throw new IllegalArgumentException("This placement is invalid");
    }
  }

  /**
   * Play one turn for this player. May choose to fire, move, or sonar scan.
   * 
   * @param Board<Character> to play on
   * @param BoardTextView of enemyBoard
   * @param String my header of the board
   * @param String enemy header of the board
   */
  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String myHeader, String enemyHeader) {
    try {
      if (getMoveTimes() == 0 && getScanTimes() == 0) {
        playOneFire(enemyBoard);
      } else {
        char c = readChoice();
        doChoice(c, enemyBoard);
      }
    } catch (IllegalArgumentException e) {
      playOneTurn(enemyBoard, enemyView, myHeader, enemyHeader);
    }
  }

  /**
   * Generate a choice from fire, move and scan
   * 
   * @return char
   */
  public char readChoice() {
    String choice = "FMS";
    char c = choice.charAt(rdm.nextInt(3));
    return c;
  }

  /**
   * Do choice based on the char.
   * 
   * 'F' for fire
   * 'M' for move
   * 'S' for scan
   * 
   * @param char for choice
   * @param Board<Character> board to play on
   */
  public void doChoice(char c, Board<Character> enemyBoard) {
    if (c == 'F') {
      playOneFire(enemyBoard);
    } else {
      if (c == 'M') {
        if (getMoveTimes() == 0) {
          throw new IllegalArgumentException("You don't have any move leaving!");
        }
        playOneMove();
      } else {
        if (getScanTimes() == 0) {
          throw new IllegalArgumentException("You don't have any sonar scan leaving!");
        }
        playOneScan();
      }
      out.println("Player " + name + " used a special action!");
    }
  }

  /**
   * Play scan for computer player.
   * 
   * Actuall just updat scanTimes here
   */
  public void playOneScan() {
    updateScanTimes();
  }
}
