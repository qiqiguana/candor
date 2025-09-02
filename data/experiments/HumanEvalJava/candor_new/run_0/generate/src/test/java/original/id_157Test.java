package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RightAngleTriangle.
*/
class RightAngleTriangleTest {
    @Test
    void testRightAngleTriangleDifferentSides() {
        // Given lengths of the three sides of a triangle, return True if they form a right-angled triangle
        boolean result = RightAngleTriangle.rightAngleTriangle(3, 4, 5);
        assertTrue(result);
    }
    
    @Test
        public void testNothing(){
            RightAngleTriangle s = new RightAngleTriangle();
            }
    @Test
    public void TestRightAngleTriangle_EqualSides_False() {
    	assertFalse(RightAngleTriangle.rightAngleTriangle(2, 2, 2));
    }
    @Test
    public void TestRightAngleTriangle_SideAIsLongest_True_2() { 
        assertTrue(RightAngleTriangle.rightAngleTriangle(13, 5, 12));
    }
    @Test
    public void RightAngleTriangle_LongestSideNotFirstOrSecond_True_6() {
        int[] sides = {24, 7, 25};
        assertAll(
            () -> assertTrue(RightAngleTriangle.rightAngleTriangle(sides[0], sides[1], sides[2]))
        );
    }
    @Test
    void testRightAngleTriangle_B_Largest_Side_NotRightAngled() {
        boolean result = RightAngleTriangle.rightAngleTriangle(5, 12, 14);
        assertFalse(result);
    }
    @Test
    public void RightAngleTriangle_TriangleWithBBiggerThanAC_2() {
        int[] sides = {25, 24, 7};
        assertTrue(RightAngleTriangle.rightAngleTriangle(sides[1], sides[0], sides[2]));
    }
    @Test
    public void testRightAngleTriangleWithTwoEqualSidesFixed4() {
    double c = Math.sqrt(2 * 2 + 3 * 3);
    assertFalse(RightAngleTriangle.rightAngleTriangle(2, 3, (int)c));
    }
    @Test
    public void testRightAngleTriangleWithEqualSides() {
        assertAll(
            () -> assertFalse(RightAngleTriangle.rightAngleTriangle(1, 2, 1)),
            () -> assertTrue(RightAngleTriangle.rightAngleTriangle(3, 4, 5))
        );
    }
                                    
}