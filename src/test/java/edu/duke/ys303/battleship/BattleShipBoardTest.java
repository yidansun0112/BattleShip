package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20,'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0,'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20,'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5,'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20,'X'));
  }

  private <T> void checkWhatIsAtBoard(BattleShipBoard<Character> b, Character[][] expect) {
    int w = b.getWidth();
    int h = b.getHeight();
    int i = 0;
    int j = 0;
    for (i = 0; i < h; i++) {
      for (j = 0; j < w; j++) {
        Coordinate c = new Coordinate(i, j);
        assertEquals(expect[i][j], b.whatIsAtForSelf(c));
      }
    }
  }

  @Test
  public void test_whatIsAt_and_addShip() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(3, 3,'X');
    Character[][] expect = new Character[3][3];
    checkWhatIsAtBoard(b, expect);
    Coordinate c1 = new Coordinate(0, 0);
    Coordinate c2 = new Coordinate(0, 2);
    Coordinate c3 = new Coordinate(1, 1);
    Ship<Character> s1 = new RectangleShip<Character>(c1, 's', '*');
    Ship<Character> s2 = new RectangleShip<Character>(c2, 's', '*');
    Ship<Character> s3 = new RectangleShip<Character>(c3, 's', '*');
   String r1 = b.tryAddShip(s1);
   assertEquals(null, r1);
    expect[0][0] = 's';
    String r2 = b.tryAddShip(s2);
    assertEquals(null, r2);
    expect[0][2] = 's';
    b.tryAddShip(s3);
    expect[1][1] = 's';
    checkWhatIsAtBoard(b, expect);
    assertEquals("That placement is invalid: the ship overlaps another ship.",b.tryAddShip(s3));
  }

  @Test
  public void test_fireAt(){
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(3, 3,'X');
    Coordinate c1 = new Coordinate(0, 0);
    Ship<Character> s1 = new RectangleShip<Character>(c1, 's', '*');
    b.tryAddShip(s1);
    Ship<Character> h1=b.fireAt(c1);
    assertSame(s1,h1);
    assertTrue(s1.isSunk());
    Coordinate c2=new  Coordinate(1,1);
    Ship<Character> h2=b.fireAt(c2);
    assertEquals(null,h2);
    assertEquals('X',b.whatIsAtForEnemy(c2));
  }

  @Test
  public void test_allSunk(){
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(3, 3,'X');
    Coordinate c1 = new Coordinate(0, 0);
    Ship<Character> s1 = new RectangleShip<Character>(c1, 's', '*');
    b.tryAddShip(s1);
    Coordinate c2 = new Coordinate(1, 1);
    Ship<Character> s2 = new RectangleShip<Character>(c2, 's', '*');
    b.tryAddShip(s2);
    b.fireAt(c1);
    assertEquals('s',b.whatIsAtForEnemy(c1));
    assertEquals(null,b.whatIsAtForEnemy(c2));
    assertEquals(false,b.allSunk());
     b.fireAt(c2);
    assertEquals(true,b.allSunk());
  }
}












