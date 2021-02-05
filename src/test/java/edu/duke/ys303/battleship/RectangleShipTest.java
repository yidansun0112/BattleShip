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
    RectangleShip<Character> s1 = new RectangleShip<Character>(new Coordinate(1, 1), 1, 3,'s','*');
    assertEquals(true, s1.occupiesCoordinates(new Coordinate(1, 1)));
    assertEquals(true, s1.occupiesCoordinates(new Coordinate(1, 2)));
    assertEquals(true, s1.occupiesCoordinates(new Coordinate(1, 3)));
    assertEquals(false, s1.occupiesCoordinates(new Coordinate(1, 4)));
    RectangleShip<Character> s2 = new RectangleShip<Character>(new Coordinate(2, 2), 3, 1,'s','*');
    assertEquals(true, s2.occupiesCoordinates(new Coordinate(2, 2)));
    assertEquals(true, s2.occupiesCoordinates(new Coordinate(3, 2)));
    assertEquals(true, s2.occupiesCoordinates(new Coordinate(4, 2)));
    assertEquals(false, s2.occupiesCoordinates(new Coordinate(1, 4)));
    assertEquals(false,s2.myPieces.get(new Coordinate(2,2)));
    assertEquals(null,s2.myPieces.get(new Coordinate(2,5)));      
  }

}











