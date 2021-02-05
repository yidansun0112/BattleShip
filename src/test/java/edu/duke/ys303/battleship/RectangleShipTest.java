package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.HashSet;

public class RectangleShipTest {
  @Test
  public void test_make_coords() {
    Coordinate uplft = new Coordinate(1, 1);
    HashSet<Coordinate> coords = RectangleShip.makeCoords(uplft, 1, 3);
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(new Coordinate(1, 1));
    expected.add(new Coordinate(1, 2));
    expected.add(new Coordinate(1, 3));
    assertEquals(expected, coords);
  }

  @Test
  public void test_constructor_and_occupy() {
    RectangleShip<Character> s1 = new RectangleShip<Character>(new Coordinate(1, 1), 1, 3, 's', '*');
    assertEquals(true, s1.occupiesCoordinates(new Coordinate(1, 1)));
    assertEquals(true, s1.occupiesCoordinates(new Coordinate(1, 2)));
    assertEquals(true, s1.occupiesCoordinates(new Coordinate(1, 3)));
    assertEquals(false, s1.occupiesCoordinates(new Coordinate(1, 4)));
    RectangleShip<Character> s2 = new RectangleShip<Character>(new Coordinate(2, 2), 3, 1, 's', '*');
    assertEquals(true, s2.occupiesCoordinates(new Coordinate(2, 2)));
    assertEquals(true, s2.occupiesCoordinates(new Coordinate(3, 2)));
    assertEquals(true, s2.occupiesCoordinates(new Coordinate(4, 2)));
    assertEquals(false, s2.occupiesCoordinates(new Coordinate(1, 4)));
    assertEquals(false, s2.myPieces.get(new Coordinate(2, 2)));
    assertEquals(null, s2.myPieces.get(new Coordinate(2, 5)));
  }

  @Test
  public void test_recordHit_and_wasHit() {
    RectangleShip<Character> ship = new RectangleShip<Character>(new Coordinate(1, 1), 1, 3, 's', '*');
    Coordinate c1 = new Coordinate(1, 1);
    Coordinate c2 = new Coordinate(1, 3);
    Coordinate c3 = new Coordinate(3, 3);
    ship.recordHitAt(c1);
    assertEquals(true, ship.wasHitAt(c1));
    assertEquals(false, ship.wasHitAt(c2));
    assertThrows(IllegalArgumentException.class, ()->ship.recordHitAt(c3));
    assertThrows(IllegalArgumentException.class, ()->ship.wasHitAt(c3));
  }

  @Test
  public void test_isSunk(){
    RectangleShip<Character> ship = new RectangleShip<Character>(new Coordinate(1, 1), 1, 3, 's', '*');
    Coordinate c1 = new Coordinate(1, 1);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(1, 3);
    ship.recordHitAt(c1);
    ship.recordHitAt(c2);
    assertEquals(false, ship.isSunk());
    ship.recordHitAt(c3);
    assertEquals(true, ship.isSunk());
  }

  @Test
  public void test_display_info(){
    RectangleShip<Character> ship = new RectangleShip<Character>(new Coordinate(1, 1), 1, 3, 's', '*');
    Coordinate c1 = new Coordinate(1, 1);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(3, 3);
    ship.recordHitAt(c1);
    assertThrows(IllegalArgumentException.class, ()->ship.getDisplayInfoAt(c3));
    assertEquals('*',ship.getDisplayInfoAt(c1));
    assertEquals('s',ship.getDisplayInfoAt(c2));
  }    
}
