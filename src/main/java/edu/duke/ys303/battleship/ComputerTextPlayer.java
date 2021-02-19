package edu.duke.ys303.battleship;

import java.util.Random;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.function.Function;

public class ComputerTextPlayer extends TextPlayer {
  /** Random object to generate numbers */
  private Random rdm;

  public ComputerTextPlayer(String name, Board<Character> theBoard, BufferedReader inputReader, PrintStream out,
      V2ShipFactory factory) {
    this(name,theBoard,inputReader,out,factory,false);
  }

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

  public Placement readPlacement() {
    int row = rdm.nextInt(theBoard.getHeight());
    int col = rdm.nextInt(theBoard.getWidth());
    String oriChoice = "VHURDL";
    char ori = oriChoice.charAt(rdm.nextInt(6));
    return new Placement(new Coordinate(row, col), ori);
  }

  public Coordinate readCoordinate() {
    int row = rdm.nextInt(theBoard.getHeight());
    int col = rdm.nextInt(theBoard.getWidth());
    Coordinate c = new Coordinate(row, col);
    return c;
  }

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

  public void doPlacementPhase() {
    for (String shipName : shipsToPlaces) {
      doOnePlacement(shipName, shipCreationFns.get(shipName));
    }
  }

  public void playOneFire(Board<Character> enemyBoard) {
    Coordinate c = readCoordinate();
    Ship<Character> s = enemyBoard.fireAt(c);
    if (s == null) {
      out.println("Player " + name + " missed!");
    } else {
      out.println("Player " + name + " hit your " + s.getName() + " at " + c.toString() + "!");
    }
  }

  public void playOneMove() {
    Coordinate c = readCoordinate();
    Ship<Character> s = theBoard.findShip(c);
    if (s == null) {
      throw new IllegalArgumentException("This coordinate is not part of any ship on the board.");
    }
    moveShip(s);
  }

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

  public char readChoice() {
    String choice = "FMS";
    char c = choice.charAt(rdm.nextInt(3));
    return c;
  }

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

  public void playOneScan() {
    updateScanTimes();
  }
}
