package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class CarrierShipTest {
  @Test
  public void test_make_up() {
    CarrierShip<Character> s = new CarrierShip<Character>("Carrier", new Placement("B1U"), 'c', '*');
    assertEquals("Carrier", s.getName());
    assertEquals(new Coordinate(1, 1), s.findCoordinate(0));
    assertEquals(new Coordinate(2, 1), s.findCoordinate(1));
    assertEquals(new Coordinate(3, 1), s.findCoordinate(2));
    assertEquals(new Coordinate(3, 2), s.findCoordinate(3));
    assertEquals(new Coordinate(4, 2), s.findCoordinate(4));
    assertEquals(new Coordinate(5, 2), s.findCoordinate(5));
  }

  @Test
  public void test_make_right() {
    CarrierShip<Character> s = new CarrierShip<Character>("Carrier", new Placement("B3R"), 'c', '*');
    assertEquals("Carrier", s.getName());
    assertEquals(new Coordinate(2, 1), s.findCoordinate(0));
    assertEquals(new Coordinate(2, 2), s.findCoordinate(1));
    assertEquals(new Coordinate(2, 3), s.findCoordinate(2));
    assertEquals(new Coordinate(1, 3), s.findCoordinate(3));
    assertEquals(new Coordinate(1, 4), s.findCoordinate(4));
    assertEquals(new Coordinate(1, 5), s.findCoordinate(5));
  }

  @Test
  public void test_make_down() {
    CarrierShip<Character> s = new CarrierShip<Character>("Carrier", new Placement("B1D"), 'c', '*');
    assertEquals("Carrier", s.getName());
    assertEquals(new Coordinate(1, 1), s.findCoordinate(5));
    assertEquals(new Coordinate(2, 1), s.findCoordinate(4));
    assertEquals(new Coordinate(3, 1), s.findCoordinate(3));
    assertEquals(new Coordinate(3, 0), s.findCoordinate(2));
    assertEquals(new Coordinate(4, 0), s.findCoordinate(1));
    assertEquals(new Coordinate(5, 0), s.findCoordinate(0));
  }

  @Test
  public void test_make_left() {
    CarrierShip<Character> s = new CarrierShip<Character>("Carrier", new Placement("B1L"), 'c', '*');
    assertEquals("Carrier", s.getName());
    assertEquals(new Coordinate(1, 1), s.findCoordinate(5));
    assertEquals(new Coordinate(1, 2), s.findCoordinate(4));
    assertEquals(new Coordinate(1, 3), s.findCoordinate(3));
    assertEquals(new Coordinate(2, 3), s.findCoordinate(2));
    assertEquals(new Coordinate(2, 4), s.findCoordinate(1));
    assertEquals(new Coordinate(2, 5), s.findCoordinate(0));
  }
}
