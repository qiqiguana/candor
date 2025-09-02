package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RightAngleTriangle.
*/
class RightAngleTriangleTest {

    @Test
    void testRightAngleTriangle() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(3, 4, 5));
    }
    
    @Test
        public void testNothing(){
            RightAngleTriangle s = new RightAngleTriangle();
            }
    @Test
    public void testRightAngleTriangleWithSides345() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(3, 4, 5));
    }
    @Test
    public void testRightAngleTriangleWithAAsLongestSide() {
    	assertTrue(RightAngleTriangle.rightAngleTriangle(10, 6, 8));
    }
    @Test
    public void testRightAngleTriangleWithBAsLongestSide() {
    	assertTrue(RightAngleTriangle.rightAngleTriangle(5, 12, 13));
    }
    @Test
    public void testRightAngleTriangleWithCAsLongestSide() {
    	assertTrue(RightAngleTriangle.rightAngleTriangle(15, 8, 17));
    }
    @Test
    public void testRightAngleTriangleWithAllSidesEqual() {
    	assertFalse(RightAngleTriangle.rightAngleTriangle(1, 1, 1));
    }
    @Test
    public void testRightAngleTriangle_bAsLongestSide() {
      int[] sides = {3, 5, 4};
      boolean result = RightAngleTriangle.rightAngleTriangle(sides[0], sides[1], sides[2]);
      assertEquals(true, result);
    }
    @Test
    public void testRightAngleTriangle_bAsLongestSide_notARightAngle() {
      int[] sides = {3, 5, 6};
      boolean result = RightAngleTriangle.rightAngleTriangle(sides[0], sides[1], sides[2]);
      assertEquals(false, result);
    }
    @Test
    public void testRightAngleTriangle_bAsLongestSide_equalSides() {
      int[] sides = {3, 5, 3};
      boolean result = RightAngleTriangle.rightAngleTriangle(sides[0], sides[1], sides[2]);
      assertEquals(false, result);
    }
    @Test
    public void testRightAngleTriangle_bAsLongestSide_aEqualsB() {
      int[] sides = {5, 5, 3};
      boolean result = RightAngleTriangle.rightAngleTriangle(sides[0], sides[1], sides[2]);
      assertEquals(false, result);
    }
    @Test
    public void testRightAngleTriangle_cAsLongestSide_notARightAngle() {
      int[] sides = {3, 4, 6};
      boolean result = RightAngleTriangle.rightAngleTriangle(sides[0], sides[1], sides[2]);
      assertEquals(false, result);
    }
    @Test
    public void testTriangleWithEqualSides() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(1, 1, 1));
    }
    @Test
    public void testRightAngleTriangleWithLargeSides() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(48, 55, 73));
    }
    @Test
    public void testScaleneRightAngleTriangle() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(5, 12, 13));
    }
                                    
}