package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.HashSet;

public class BattleShipTest {
  @Test
  public void test_make_up() {
    BattleShip<Character> s = new BattleShip<Character>("Battleship", new Placement("B1U"), 'b', '*');
    assertEquals("Battleship", s.getName());
    assertEquals(new Coordinate(1, 1), s.findCoordinate(0));
    assertEquals(new Coordinate(2, 0), s.findCoordinate(1));
    assertEquals(new Coordinate(2, 1), s.findCoordinate(2));
    assertEquals(new Coordinate(2, 2), s.findCoordinate(3));
  }

  @Test
  public void test_make_right() {
    BattleShip<Character> s = new BattleShip<Character>("Battleship", new Placement("B1R"), 'b', '*');
    assertEquals(new Coordinate(2, 2), s.findCoordinate(0));
    assertEquals(new Coordinate(1, 1), s.findCoordinate(1));
    assertEquals(new Coordinate(2, 1), s.findCoordinate(2));
    assertEquals(new Coordinate(3, 1), s.findCoordinate(3));
  }

  @Test
  public void test_make_down() {
    BattleShip<Character> s = new BattleShip<Character>("Battleship", new Placement("B1D"), 'b', '*');
    assertEquals(new Coordinate(2, 2), s.findCoordinate(0));
    assertEquals(new Coordinate(1, 3), s.findCoordinate(1));
    assertEquals(new Coordinate(1, 2), s.findCoordinate(2));
    assertEquals(new Coordinate(1, 1), s.findCoordinate(3));
  }

  @Test
  public void test_make_left() {
    BattleShip<Character> s = new BattleShip<Character>("Battleship", new Placement("B1L"), 'b', '*');
    assertEquals(new Coordinate(2, 0), s.findCoordinate(0));
    assertEquals(new Coordinate(3, 1), s.findCoordinate(1));
    assertEquals(new Coordinate(2, 1), s.findCoordinate(2));
    assertEquals(new Coordinate(1, 1), s.findCoordinate(3));
  }
}










