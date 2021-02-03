package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {

  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>(w, h);
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_2by2() {
    String expectedHeader = "  0|1\n";
    String expectedBody = "A  |  A\n" + "B  |  B\n";
    emptyBoardHelper(2, 2, expectedHeader, expectedBody);
  }

  @Test
  public void test_display_empty_3by2() {
    String expectedHeader = "  0|1|2\n";
    String expectedBody = "A  | |  A\n" + "B  | |  B\n";
    emptyBoardHelper(3, 2, expectedHeader, expectedBody);
  }

  @Test
  public void test_display_empty_3by5() {
    String expectedHeader = "  0|1|2\n";
    String expectedBody = "A  | |  A\n" + "B  | |  B\n" + "C  | |  C\n" + "D  | |  D\n" + "E  | |  E\n";
    emptyBoardHelper(3, 5, expectedHeader, expectedBody);
  }

  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20);
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27);
    // you should write two assertThrows here
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

  @Test
  public void test_display_board_with_ships_4by3(){
    Board<Character> b= new BattleShipBoard<Character>(4, 3);
    Coordinate c1=new Coordinate(0,0);
    Coordinate c2=new Coordinate(1,1);
    Ship<Character> s1=new BasicShip(c1);
    Ship<Character> s2=new BasicShip(c2);
    b.tryAddShip(s1);
    b.tryAddShip(s2);
    String e1="  0|1|2|3\n"+"A s| | |  A\n"+"B  |s| |  B\n"+"C  | | |  C\n"+"  0|1|2|3\n";
    BoardTextView v1=new BoardTextView(b);
    assertEquals(e1,v1.displayMyOwnBoard());
    Coordinate c3=new Coordinate(2,3);
    Ship<Character> s3=new BasicShip(c3);
    b.tryAddShip(s3);
    String e2="  0|1|2|3\n"+"A s| | |  A\n"+"B  |s| |  B\n"+"C  | | |s C\n"+"  0|1|2|3\n";
    BoardTextView v2=new BoardTextView(b);
    assertEquals(e2,v2.displayMyOwnBoard());
  }
}










