package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  @Test
  public void test_no_collisions() {
    V1ShipFactory f = new V1ShipFactory();
    Placement p = new Placement("B1v");
    Ship<Character> s1 = f.makeSubmarine(p);
    Board<Character> b = new BattleShipBoard<Character>(3, 3);
    NoCollisionRuleChecker<Character> r = new NoCollisionRuleChecker<Character>(null);
    assertEquals(true, r.checkPlacement(s1, b));
    b.tryAddShip(s1);
    Ship<Character> s2 = f.makeBattleship(p);
    assertEquals(false, r.checkPlacement(s2, b));
  }

  @Test
  public void test_rules_checker() {
    V1ShipFactory f = new V1ShipFactory();
    Placement p = new Placement("B1v");
    Ship<Character> s1 = f.makeSubmarine(p);
    NoCollisionRuleChecker<Character> r = new NoCollisionRuleChecker<Character>(null);
    InBoundsRuleChecker<Character> r2 = new InBoundsRuleChecker<Character>(r);
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(3, 3, r2);
    assertEquals(true, r2.checkPlacement(s1, b));
  }

}
