package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_bound_checker() {
    V1ShipFactory f = new V1ShipFactory();
    Placement p = new Placement("B1v");
    Ship<Character> s1 = f.makeSubmarine(p);
    Ship<Character> s2 = f.makeBattleship(p);
    Placement p1 = new Placement("B1H");
    Ship<Character> s3 = f.makeBattleship(p1);
    InBoundsRuleChecker<Character> r = new InBoundsRuleChecker<Character>(null);
    Board<Character> b = new BattleShipBoard<Character>(3, 3, r);
    assertEquals(true, r.checkPlacement(s1, b));
    assertEquals(false, r.checkPlacement(s2, b));
    assertEquals(false, r.checkPlacement(s3, b));
  }

}
