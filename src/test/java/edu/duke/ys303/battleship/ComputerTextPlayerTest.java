package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class ComputerTextPlayerTest {
  private ComputerTextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    return new ComputerTextPlayer("A", board, input, output, shipFactory, true);
  }

  @Test
  void test_readPlacement_readCoordinate() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ComputerTextPlayer player = createTextPlayer(10, 20, "", bytes);
    assertEquals(new Placement("R8L"), player.readPlacement());
    assertEquals(new Coordinate("m1"), player.readCoordinate());
  }

  @Test
  void test_playOneTurn() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ComputerTextPlayer player = createTextPlayer(10, 20, "", bytes);
    player.doPlacementPhase();
    for (int i = 0; i < 20; i++) {
      player.playOneTurn(player.theBoard, player.view, null, null);
    }
  }

  @Test
  void test_throw() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ComputerTextPlayer player = createTextPlayer(10, 20, "", bytes);
    player.updateMoveTimes();
    player.updateMoveTimes();
    player.updateMoveTimes();
    assertThrows(IllegalArgumentException.class, () -> player.doChoice('M', player.theBoard));
    player.updateScanTimes();
    player.updateScanTimes();
    player.updateScanTimes();
    player.playOneTurn(player.theBoard, player.view, null, null);
  }
}
