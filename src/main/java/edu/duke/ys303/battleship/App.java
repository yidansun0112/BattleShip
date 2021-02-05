/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ys303.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

public class App {

  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  
  /**
   * Constructor for App
   *
   * @param Board       to play with
   * @param Reader      for input
   * @param PrintSteeam for output
   */
  public App(Board<Character> theBoard, Reader inputSource, PrintStream out) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = new BufferedReader(inputSource);
    this.out = out;
    this.shipFactory = new V1ShipFactory();
  }

  /**
   * Give out a prompt, and receive a placement from input.
   *
   * @param String prompt to ask for input.
   * @return a Placement.
   * @throws IOException when Input String is in wrong format. Input String should
   *                     of length three. The first character is an UpperLetter,
   *                     second be a number, and third be an UpperLetter.
   */
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    return new Placement(s);
  }

  /**
   * Readin a String, construct a placement and place it on the board.
   *
   * @throws IOException when Input String is in wrong format. Input String should
   *                     of length three. The first character is an UpperLetter,
   *                     second be a number, and third be an UpperLetter.
   */
  public void doOnePlacement() throws IOException {
    Placement p = readPlacement("Where would you like to put your ship?");
    Ship<Character> s  = shipFactory.makeDestroyer(p);
    theBoard.tryAddShip(s);
    BoardTextView view = new BoardTextView(theBoard);
    out.println(view.displayMyOwnBoard());
  }

  /**
   * Main of App
   *
   * Make a board, put one placement, and print out the board.
   */
  public static void main(String[] args) throws IOException {
    Board<Character> b = new BattleShipBoard<Character>(10, 20);
    App app = new App(b, new InputStreamReader(System.in), System.out);
    app.doOnePlacement();
  }
}
