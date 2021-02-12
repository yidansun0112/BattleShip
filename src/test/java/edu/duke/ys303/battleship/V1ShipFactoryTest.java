package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {

  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter,
      Coordinate... expectedLocs) {
    assertEquals(expectedName, testShip.getName());
    for (Coordinate c : expectedLocs) {
      assertEquals(true, testShip.occupiesCoordinates(c));
    }
    assertEquals(expectedLetter, testShip.getDisplayInfoAt(expectedLocs[0],true));
  }

  @Test
  public void test_make_submarine() {
    V1ShipFactory f = new V1ShipFactory();
    Placement p1 = new Placement(new Coordinate(1, 2), 'H');
    Ship<Character> dst = f.makeSubmarine(p1);
    checkShip(dst, "Submarine", 's', new Coordinate(1, 2), new Coordinate(1, 3));
    assertEquals(false, dst.occupiesCoordinates(new Coordinate(1, 4)));
  }

  @Test
  public void test_make_destroyer() {
    V1ShipFactory f = new V1ShipFactory();
    Placement p1 = new Placement(new Coordinate(1, 1), 'V');
    Ship<Character> dst = f.makeDestroyer(p1);
    checkShip(dst, "Destroyer", 'd', new Coordinate(1, 1), new Coordinate(2, 1), new Coordinate(3, 1));
    assertEquals(false, dst.occupiesCoordinates(new Coordinate(4, 1)));
  }

  @Test
  public void test_make_battleship() {
    V1ShipFactory f = new V1ShipFactory();
    Placement p1 = new Placement(new Coordinate(1, 1), 'V');
    Ship<Character> dst = f.makeBattleship(p1);
    checkShip(dst, "Battleship", 'b', new Coordinate(1, 1), new Coordinate(2, 1), new Coordinate(3, 1),
        new Coordinate(4, 1));
    assertEquals(false, dst.occupiesCoordinates(new Coordinate(5, 1)));
  }

  @Test
  public void test_make_carrier() {
    V1ShipFactory f = new V1ShipFactory();
    Placement p1 = new Placement(new Coordinate(1, 1), 'V');
    Ship<Character> dst = f.makeCarrier(p1);
    checkShip(dst, "Carrier", 'c', new Coordinate(1, 1), new Coordinate(2, 1), new Coordinate(3, 1),
        new Coordinate(4, 1), new Coordinate(5, 1), new Coordinate(6, 1));
    assertEquals(false, dst.occupiesCoordinates(new Coordinate(7, 1)));
  }

  @Test
  public void test_throw() {
    V1ShipFactory f = new V1ShipFactory();
    Placement p1 = new Placement(new Coordinate(1, 1), 'f');
    assertThrows(IllegalArgumentException.class, () -> f.makeCarrier(p1));
  }
}
