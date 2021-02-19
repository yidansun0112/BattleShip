package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ComputerTextPlayerTest {
  private ComputerTextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    return new ComputerTextPlayer("A", board, input, output, shipFactory,true);
  }

  @Disabled
  @Test
  void test_computer()  {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ComputerTextPlayer player = createTextPlayer(10, 20, "", bytes);
    String move = "Player A used a special action!\n";
    String miss = "Player A missed!\n";
    String hit = "Player A hit your Submarine at (12,1)!\n";
    player.doPlacementPhase();
    player.playOneTurn(player.theBoard, player.view, null, null);
    player.playOneTurn(player.theBoard, player.view, null, null);
    player.playOneTurn(player.theBoard, player.view, null, null);
    player.playOneTurn(player.theBoard, player.view, null, null);
    player.playOneTurn(player.theBoard, player.view, null, null);
    assertEquals(move + move + move + miss + hit, bytes.toString());
  }

  @Test
  void test_readPlacement_readCoordinate(){
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ComputerTextPlayer player = createTextPlayer(10, 20, "", bytes);
    assertEquals(new Placement("R8L"),player.readPlacement());
    assertEquals(new Coordinate("m1"),player.readCoordinate());
  }
}
