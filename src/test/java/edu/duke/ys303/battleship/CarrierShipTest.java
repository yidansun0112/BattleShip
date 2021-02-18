package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class CarrierShipTest {
  @Test
  public void test_make_up() {
    CarrierShip<Character> s = new CarrierShip<Character>("Carrier", new Placement("B1U"), 'c', '*');
    assertEquals("Carrier", s.getName());
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(new Coordinate(1, 1));
    expected.add(new Coordinate(2, 1));
    expected.add(new Coordinate(3, 1));
    expected.add(new Coordinate(3, 2));
    expected.add(new Coordinate(4, 2));
    expected.add(new Coordinate(5, 2));
    assertEquals(expected, s.getCoordinates());
  }

  @Test
  public void test_make_right() {
    CarrierShip<Character> s = new CarrierShip<Character>("Carrier", new Placement("B3R"), 'c', '*');
    assertEquals("Carrier", s.getName());
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(new Coordinate(2, 1));
    expected.add(new Coordinate(2, 2));
    expected.add(new Coordinate(2, 3));
    expected.add(new Coordinate(1, 3));
    expected.add(new Coordinate(1, 4));
    expected.add(new Coordinate(1, 5));
    assertEquals(expected, s.getCoordinates());
  }

  @Test
  public void test_make_down() {
    CarrierShip<Character> s = new CarrierShip<Character>("Carrier", new Placement("B1D"), 'c', '*');
    assertEquals("Carrier", s.getName());
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(new Coordinate(1, 1));
    expected.add(new Coordinate(2, 1));
    expected.add(new Coordinate(3, 1));
    expected.add(new Coordinate(3, 0));
    expected.add(new Coordinate(4, 0));
    expected.add(new Coordinate(5, 0));
    assertEquals(expected, s.getCoordinates());
  }

  @Test
  public void test_make_left() {
    CarrierShip<Character> s = new CarrierShip<Character>("Carrier", new Placement("B1L"), 'c', '*');
    assertEquals("Carrier", s.getName());
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(new Coordinate(1, 1));
    expected.add(new Coordinate(1, 2));
    expected.add(new Coordinate(1, 3));
    expected.add(new Coordinate(2, 3));
    expected.add(new Coordinate(2, 4));
    expected.add(new Coordinate(2, 5));
    assertEquals(expected, s.getCoordinates());
  }
}











