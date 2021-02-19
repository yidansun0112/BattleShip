package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {

  private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes, boolean seed) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory, seed);
  }

  @Test
  void test_read_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes, false);
    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');
    for (int i = 0; i < expected.length; i++) {
      Placement p = player.readPlacement(prompt);
      assertEquals(p, expected[i]); // did we get the right Placement back
      assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
      bytes.reset(); // clear out bytes for next time around
    }
  }

  @Test
  void test_EOFException() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "", bytes, false);
    String prompt = "Please enter a location for a ship:";
    assertThrows(EOFException.class, () -> player.readPlacement(prompt));
    assertThrows(EOFException.class, () -> player.readCoordinate(prompt));
  }

  @Test
  public void test_do_one_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(4, 3, "A1V\n", bytes, false);
    String prompt = "Player A where do you want to place a Destroyer?\n";
    String e1 = "  0|1|2|3\n" + "A  |d| |  A\n" + "B  |d| |  B\n" + "C  |d| |  C\n" + "  0|1|2|3\n";
    player.doOnePlacement("Destroyer", (p) -> player.shipFactory.makeDestroyer(p));
    assertEquals(prompt + e1, bytes.toString());
  }

  @Test
  public void test_isLose() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(4, 3, "A1V\n", bytes, false);
    player.doOnePlacement("Destroyer", (p) -> player.shipFactory.makeDestroyer(p));
    assertEquals(false, player.isLose());
  }

  @Test
  public void test_sonar_scan() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "d3\n", bytes, false);
    player.theBoard.tryAddShip(player.shipFactory.makeSubmarine(new Placement("A1V")));
    player.theBoard.tryAddShip(player.shipFactory.makeSubmarine(new Placement("C3V")));
    player.theBoard.tryAddShip(player.shipFactory.makeSubmarine(new Placement("C1h")));
    player.theBoard.tryAddShip(player.shipFactory.makeSubmarine(new Placement("C5h")));
    player.theBoard.tryAddShip(player.shipFactory.makeSubmarine(new Placement("f2v")));
    player.theBoard.tryAddShip(player.shipFactory.makeSubmarine(new Placement("f4v")));
    player.playOneScan(player.theBoard);
    String expected = "Submarines occupy 7 squares\nDestroyers occupy 0 squares\nBattleships occupy 0 squares\nCarriers occupy 0 squares\n";
    String prompt = "Player A please choose one center coordinate to sonar scan!\n";
    assertEquals(prompt + expected, bytes.toString());
  }

  @Test
  void test_computer() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "c\n", bytes, true);
    String prompt = "Do you want Player A a human player or to be played by the computer?\n"
        + "H for human, C for computer\n" + "Please make your choice:\n";
    String move = "Player A used a special action!\n";
    String miss = "Player A missed!\n";
    String hit = "Player A hit your Submarine at (12,1)!\n";
    player.decideHumanComputer();
    player.doPlacementPhase();
    player.playOneTurn(player.theBoard, player.view, null, null);
    player.playOneTurn(player.theBoard, player.view, null, null);
    player.playOneTurn(player.theBoard, player.view, null, null);
    player.playOneTurn(player.theBoard, player.view, null, null);
    player.playOneTurn(player.theBoard, player.view, null, null);
    assertEquals(prompt + move + move + move + miss + hit, bytes.toString());
  }
}
