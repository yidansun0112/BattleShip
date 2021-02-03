package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_Coordinate_and_orientation() {
    Coordinate c1 = new Coordinate(10, 20);
    Placement p1 = new Placement(c1, 'V');
    assertEquals(c1, p1.getWhere());
    assertEquals('V', p1.getOrientation());
    Placement p2 = new Placement("A3V");
    Coordinate c2=new Coordinate(0,3);
    assertEquals(c2, p2.getWhere());
    assertEquals('V', p2.getOrientation());
  }

  @Test
  public void test_toString() {
    Coordinate c1 = new Coordinate(1, 2);
    Placement p1 = new Placement(c1, 'V');
    assertEquals("((1,2),V)", p1.toString());
  }

  @Test
  public void test_hashCode() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(0, 3);
    Coordinate c4 = new Coordinate(2, 1);
    Placement p1 = new Placement(c1, 'V');
    Placement p2 = new Placement(c2, 'V');
    Placement p3 = new Placement(c3, 'V');
    Placement p4 = new Placement(c4, 'v');
    assertEquals(p1.hashCode(), p2.hashCode());
    assertNotEquals(p1.hashCode(), p3.hashCode());
    assertNotEquals(p1.hashCode(), p4.hashCode());
  }

  @Test
  public void test_equals() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(1, 3);
    Coordinate c4 = new Coordinate(3, 2);
    Placement p1 = new Placement(c1, 'V');
    Placement p2 = new Placement(c2, 'v');
    Placement p3 = new Placement(c1, 'H');
    Placement p4 = new Placement(c3, 'V');
    assertEquals(p1, p1); // equals should be reflexsive          
    assertEquals(p1, p2); // different objects bu same contents   
    assertNotEquals(p1, p3); // different contents                
    assertNotEquals(p1, p4);                                      
    assertNotEquals(p3, p4);                                      
    assertNotEquals(p1, "((1, 2),V)"); // different types          
  }
}
