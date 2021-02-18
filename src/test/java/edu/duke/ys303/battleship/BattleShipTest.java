package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.HashSet;

public class BattleShipTest {
  @Test
  public void test_make_up() {
    BattleShip<Character> s = new BattleShip<Character>("Battleship", new Placement("B1U"), 'b', '*');
    assertEquals("Battleship",s.getName());
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(new Coordinate(1, 1));
    expected.add(new Coordinate(2, 0));
    expected.add(new Coordinate(2, 1));
    expected.add(new Coordinate(2, 2));
    assertEquals(expected,s.getCoordinates());
  }


  @Test
  public void test_make_right() {
    BattleShip<Character> s = new BattleShip<Character>("Battleship", new Placement("B1R"), 'b', '*');
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(new Coordinate(1, 1));
    expected.add(new Coordinate(2, 1));
    expected.add(new Coordinate(3, 1));
    expected.add(new Coordinate(2, 2));
    assertEquals(expected,s.getCoordinates());
  }

  @Test
  public void test_make_down() {
    BattleShip<Character> s = new BattleShip<Character>("Battleship", new Placement("B1D"), 'b', '*');
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(new Coordinate(1, 1));
    expected.add(new Coordinate(1, 2));
    expected.add(new Coordinate(1, 3));
    expected.add(new Coordinate(2, 2));
    assertEquals(expected,s.getCoordinates());
  }

  @Test
  public void test_make_left() {
    BattleShip<Character> s = new BattleShip<Character>("Battleship", new Placement("B1L"), 'b', '*');
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(new Coordinate(1, 1));
    expected.add(new Coordinate(2, 0));
    expected.add(new Coordinate(2, 1));
    expected.add(new Coordinate(3, 1));
    assertEquals(expected,s.getCoordinates());
  }
}







