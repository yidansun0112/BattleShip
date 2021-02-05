package edu.duke.ys303.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_getInfo() {
    SimpleShipDisplayInfo<Character> ship=new SimpleShipDisplayInfo<Character>('a', 'b');
    Coordinate c=new Coordinate(1,1);
    assertEquals('a',ship.getInfo(c,false));
    assertEquals('b',ship.getInfo(c,true));      
  }

}











