package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BasicShipTest {
  @Test
  public void test_constructor_coordinate() {
    Coordinate c1 = new Coordinate(1, 1);
    Coordinate c2 = new Coordinate(2, 2);
    RectangleShip<Character> s1 = new RectangleShip<Character>(c1, 's', '*');
    assertEquals(false, s1.myPieces.get(c1));
    assertEquals(null, s1.myPieces.get(c2));
  }
}
