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
    public void RightAngleTriangle_EqualSides_ReturnsFalse_1() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(2, 2, 2));
    }
    @Test
    public void RightAngleTriangle_PythagoreanTripleAIsHypotenuse_ReturnsTrue_2() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(5, 3, 4));
    }
    @Test
    public void RightAngleTriangle_PythagoreanTripleBIsHypotenuse_ReturnsTrue_3() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(3, 5, 4));
    }
    @Test
    public void RightAngleTriangle_PythagoreanTripleCIsHypotenuse_ReturnsTrue_4() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(3, 4, 5));
    }
    @Test
    public void RightAngleTriangle_NotPythagoreanTriple_ReturnsFalse_5() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(1, 2, 3));
    }
    @Test
    public void RightAngleTriangle_SidesNotPositive_ReturnsFalse_6() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(-1, 2, 3));
    }
    @Test
    void rightAngleTriangle_EqualSides_False() {
        assertEquals(false, RightAngleTriangle.rightAngleTriangle(2, 2, 2));
    }
    @Test
    void rightAngleTriangle_SideAIsLongest_True() {
        assertEquals(true, RightAngleTriangle.rightAngleTriangle(5, 3, 4));
    }
    @Test
    void rightAngleTriangle_SideBIsLongest_True() {
        assertEquals(true, RightAngleTriangle.rightAngleTriangle(3, 5, 4));
    }
    @Test
    void rightAngleTriangle_SideCIsLongest_False() {
        assertEquals(false, RightAngleTriangle.rightAngleTriangle(3, 4, 10));
    }
    @Test
    void rightAngleTriangle_SideAIsLongest_False() {
        assertEquals(false, RightAngleTriangle.rightAngleTriangle(7, 2, 3));
    }
    @Test
    void testRightAngleTriangleWithEqualSides() {
        int[] input = {3, 3, 5};
        assertFalse(RightAngleTriangle.rightAngleTriangle(input[0], input[1], input[2]));
    }
    @Test
    void testRightAngleTriangleWithLargestSideAsFirstArgument() {
        int[] input = {5, 3, 4};
        assertTrue(RightAngleTriangle.rightAngleTriangle(input[0], input[1], input[2]));
    }
    @Test
    void testRightAngleTriangleWithLargestSideAsSecondArgument() {
        int[] input = {3, 5, 4};
        assertTrue(RightAngleTriangle.rightAngleTriangle(input[0], input[1], input[2]));
    }
    @Test
    void testNonRightAngleTriangleWithSidesInIncreasingOrder() {
        int[] input = {1, 2, 3};
        assertFalse(RightAngleTriangle.rightAngleTriangle(input[0], input[1], input[2]));
    }
                                    
}