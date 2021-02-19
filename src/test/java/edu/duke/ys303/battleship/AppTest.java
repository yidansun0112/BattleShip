/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.parallel.Resources;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.ResourceAccessMode;

class AppTest {

  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main(String inputName, String outputName) throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    InputStream input = getClass().getClassLoader().getResourceAsStream(inputName);
    assertNotNull(input);
    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream(outputName);
    assertNotNull(expectedStream);
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
    try {
      System.setIn(input);
      System.setOut(out);
      App.main(new String[0]);
    } finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();
    assertEquals(expected, actual);
  }

  @Test
  void test_h_h() throws IOException {
    test_main("input.txt", "output.txt");
  }
  
  @Test
  void test_computer()throws IOException{
    BufferedReader input = new BufferedReader(new StringReader("c\n"));
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(10, 20, 'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    TextPlayer p=App.decideHumanComputer("A",board,input,output,shipFactory);
    assertTrue(p instanceof ComputerTextPlayer);
  }
}








