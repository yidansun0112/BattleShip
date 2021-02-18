package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.HashSet;

public class RectangleShipTest {
  @Test
  public void test_make_coords() {
    Coordinate uplft = new Coordinate(1, 1);
    HashMap<Integer,Coordinate> coords = RectangleShip.makeCoords(uplft, 1, 3);
    HashMap<Integer,Coordinate> expected = new HashMap<Integer,Coordinate>();
    expected.put(0,new Coordinate(1, 1));
    expected.put(1,new Coordinate(1, 2));
    expected.put(2,new Coordinate(1, 3));
    assertEquals(expected, coords);
  }

  @Test
  public void test_constructor_and_occupy_getName() {
    RectangleShip<Character> s1 = new RectangleShip<Character>("submarine", new Coordinate(1, 1), 1, 3, 's', '*');
    assertEquals("submarine", s1.getName());
    assertEquals(true, s1.occupiesCoordinates(new Coordinate(1, 1)));
    assertEquals(true, s1.occupiesCoordinates(new Coordinate(1, 2)));
    assertEquals(true, s1.occupiesCoordinates(new Coordinate(1, 3)));
    assertEquals(false, s1.occupiesCoordinates(new Coordinate(1, 4)));
    RectangleShip<Character> s2 = new RectangleShip<Character>("submarine", new Coordinate(2, 2), 3, 1, 's', '*');
    assertEquals(true, s2.occupiesCoordinates(new Coordinate(2, 2)));
    assertEquals(true, s2.occupiesCoordinates(new Coordinate(3, 2)));
    assertEquals(true, s2.occupiesCoordinates(new Coordinate(4, 2)));
    assertEquals(false, s2.occupiesCoordinates(new Coordinate(1, 4)));
    assertEquals(false, s2.myPieces.get(new Coordinate(2, 2)));
    assertEquals(null, s2.myPieces.get(new Coordinate(2, 5)));
  }

  @Test
  public void test_recordHit_and_wasHit() {
    RectangleShip<Character> ship = new RectangleShip<Character>("submarine", new Coordinate(1, 1), 1, 3, 's', '*');
    Coordinate c1 = new Coordinate(1, 1);
    Coordinate c2 = new Coordinate(1, 3);
    Coordinate c3 = new Coordinate(3, 3);
    ship.recordHitAt(c1);
    assertEquals(true, ship.wasHitAt(c1));
    assertEquals(false, ship.wasHitAt(c2));
    assertThrows(IllegalArgumentException.class, () -> ship.recordHitAt(c3));
    assertThrows(IllegalArgumentException.class, () -> ship.wasHitAt(c3));
  }

  @Test
  public void test_isSunk() {
    RectangleShip<Character> ship = new RectangleShip<Character>("submarine", new Coordinate(1, 1), 1, 3, 's', '*');
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
  public void test_display_info() {
    RectangleShip<Character> ship = new RectangleShip<Character>("submarine", new Coordinate(1, 1), 1, 3, 's', '*');
    Coordinate c1 = new Coordinate(1, 1);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(3, 3);
    ship.recordHitAt(c1);
    assertThrows(IllegalArgumentException.class, () -> ship.getDisplayInfoAt(c3,true));
    assertEquals('*', ship.getDisplayInfoAt(c1,true));
    assertEquals('s', ship.getDisplayInfoAt(c2,true));
    assertEquals('s', ship.getDisplayInfoAt(c1,false));
    assertEquals(null, ship.getDisplayInfoAt(c2,false));
  }

  @Test
  public void test_getCoordinates() {
    RectangleShip<Character> ship = new RectangleShip<Character>("submarine", new Coordinate(1, 1), 1, 3, 's', '*');
    Coordinate c1 = new Coordinate(1, 1);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(1, 3);
    HashSet<Coordinate> s = new HashSet<Coordinate>();
    s.add(c1);
    s.add(c2);
    s.add(c3);
    assertEquals(s, ship.getCoordinates());
  }
}
