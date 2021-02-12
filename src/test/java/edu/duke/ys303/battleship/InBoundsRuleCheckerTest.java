package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_bound_checker() {
    V1ShipFactory f = new V1ShipFactory();
    InBoundsRuleChecker<Character> r = new InBoundsRuleChecker<Character>(null);
    Board<Character> b = new BattleShipBoard<Character>(3, 3, r,'X');
    Placement p = new Placement("B1v");
    Ship<Character> s = f.makeSubmarine(p);
    assertEquals(null, r.checkPlacement(s, b));
    Placement p1 = new Placement(new Coordinate(-1,1),'V');
    Ship<Character> s1 = f.makeBattleship(p1);
    assertEquals("That placement is invalid: the ship goes off the top of the board.", r.checkPlacement(s1, b));
    Placement p2 = new Placement("C1V");
    Ship<Character> s2 = f.makeBattleship(p2);
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.", r.checkPlacement(s2, b));
    Placement p3 = new Placement(new Coordinate(1,-1),'V');
    Ship<Character> s3 = f.makeSubmarine(p3);
    assertEquals("That placement is invalid: the ship goes off the left of the board.", r.checkPlacement(s3, b));
    Placement p4 = new Placement(new Coordinate(1,4),'V');
    Ship<Character> s4 = f.makeSubmarine(p4);
    assertEquals("That placement is invalid: the ship goes off the right of the board.", r.checkPlacement(s4, b));
  }

}








