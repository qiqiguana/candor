package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RightAngleTriangle.
*/
class RightAngleTriangleTest {
    @Test
    void testRightAngleTriangle_3_4_5() {
        Boolean result = RightAngleTriangle.rightAngleTriangle(3, 4, 5);
        assertTrue(result);
    }
    
    @Test
        void testNothing(){
            RightAngleTriangle s = new RightAngleTriangle();
            }
    @Test
    public void testRightAngleTriangleWithValidInput() {
        assertEquals(true, RightAngleTriangle.rightAngleTriangle(3, 4, 5));
    }
    @Test
    public void testNotARightAngleTriangle() {
        assertEquals(false, RightAngleTriangle.rightAngleTriangle(1, 2, 3));
    }
    @Test
    public void testSidesAreEqual() {
        assertEquals(false, RightAngleTriangle.rightAngleTriangle(5, 5, 5));
    }
    @Test
    public void testTwoSidesAreEqual() {
        assertEquals(false, RightAngleTriangle.rightAngleTriangle(3, 4, 4));
    }
    @Test
    public void testNegativeInputValues2Fixed() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(1, -2, 3));
    }
    @Test
    public void testRightAngleTriangle_Positive() {
        int[] sides = {3, 4, 5};
        boolean result = RightAngleTriangle.rightAngleTriangle(sides[0], sides[1], sides[2]);
        assertTrue(result);
    }
    @Test
    public void testRightAngleTriangle_Negative() {
        int[] sides = {1, 2, 3};
        boolean result = RightAngleTriangle.rightAngleTriangle(sides[0], sides[1], sides[2]);
        assertFalse(result);
    }
    @Test
    public void testRightAngleTriangle_EqualSides() {
        int[] sides = {2, 2, 2};
        boolean result = RightAngleTriangle.rightAngleTriangle(sides[0], sides[1], sides[2]);
        assertFalse(result);
    }
    @Test
    public void testRightAngleTriangle_LargeNumbers() {
        int[] sides = {48, 55, 73};
        boolean result = RightAngleTriangle.rightAngleTriangle(sides[0], sides[1], sides[2]);
        assertTrue(result);
    }
    @Test
    public void testRightAngleTriangle_AnotherRightAngle() {
        int[] sides = {5, 12, 13};
        boolean result = RightAngleTriangle.rightAngleTriangle(sides[0], sides[1], sides[2]);
        assertTrue(result);
    }
    @Test
    void testRightAngleTriangleValidInput() {
        Boolean result = RightAngleTriangle.rightAngleTriangle(3, 4, 5);
        assertTrue(result);
    }
    @Test
    public void testRightAngleTriangle_ZeroSideLength() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(0, 3, 4));
    }
    @Test
    public void testRightAngleTriangle_Scalene() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(8, 15, 17));
    }
    @Test
    void testRightAngleTriangleValidInputs() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(3, 4, 5));
    }
    @Test
    public void testRightAngleTriangle_DifferentLengths() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(7, 24, 25));
    }
    @Test
    public void testRightAngleTriangle_EqualSides_Fixed() {
        assertEquals(false, RightAngleTriangle.rightAngleTriangle(2, 2, 2));
    }
    @Test
    public void testRightAngleTriangle_IsoscelesFixed() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(5, 5, 7));
    }
    @Test
    public void RightAngleTriangle_HappyPath() {
        int a = 3;
        int b = 4;
        int c = 5;
        assertTrue(RightAngleTriangle.rightAngleTriangle(a, b, c));
    }
    @Test
    public void RightAngleTriangle_SadPath() {
        int a = 1;
        int b = 2;
        int c = 3;
        assertFalse(RightAngleTriangle.rightAngleTriangle(a, b, c));
    }
    @Test
    public void RightAngleTriangle_EdgeCaseEqualSides() {
        int a = 2;
        int b = 2;
        int c = 2;
        assertFalse(RightAngleTriangle.rightAngleTriangle(a, b, c));
    }
    @Test
    public void RightAngleTriangle_EdgeCaseLargeNumbers() {
        int a = 48;
        int b = 55;
        int c = 73;
        assertTrue(RightAngleTriangle.rightAngleTriangle(a, b, c));
    }
    @Test
    public void RightAngleTriangle_NegativeTestZeroInput() {
        int a = 0;
        int b = 4;
        int c = 5;
        assertFalse(RightAngleTriangle.rightAngleTriangle(a, b, c));
    }
    @Test
    public void testNotRightAngleTriangleValidInputs() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(1, 2, 3));
    }
    @Test
    public void testRightAngleTriangleEqualSideLengths() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(5, 5, 5));
    }
    @Test
    public void testNotRightAngleTriangleTwoEqualSideLengths() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(2, 2, 3));
    }
    @Test
    public void testRightAngleTriangleNegativeInputs() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(-3, -4, -5));
    }
    @Test
    public void testRightAngleTriangleValidInputs2() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(8, 15, 17));
    }
    @Test
    public void testRightAngleTriangle_ValidInputs_1() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(3, 4, 5));
    }
    @Test
    public void RightAngleTriangle_PositiveTest_3_4_5() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(3, 4, 5));
    }
    @Test
    public void RightAngleTriangle_NegativeTest_1_2_3() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(1, 2, 3));
    }
    @Test
    public void RightAngleTriangle_EdgeCaseTest_DuplicateSides_2_2_2() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(2, 2, 2));
    }
    @Test
    public void RightAngleTriangle_EdgeCaseTest_SingleSide_0_1_1() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(0, 1, 1));
    }
    @Test
    public void RightAngleTriangle_PositiveTest_6_8_10() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(6, 8, 10));
    }
    @Test
    public void RightAngleTriangle_NegativeTest_EqualSides_1() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(1, 1, 1));
    }
    @Test
    public void RightAngleTriangle_PositiveTest_SmallNumbers() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(3, 4, 5));
    }
    @Test
    public void RightAngleTriangle_NegativeTest_NotARightAngledTriangle() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(1, 2, 3));
    }
    @Test
    public void RightAngleTriangle_EdgeCaseTest_DuplicateSides() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(5, 5, 10));
    }
    @Test
    public void RightAngleTriangle_EdgeCaseTest_ZeroLengthSide() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(0, 3, 4));
    }
    @Test
    public void RightAngleTriangle_SpecificFunctionalityTest_PythagoreanTriple() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(7, 24, 25));
    }
    @Test
    public void testRightAngleTriangle() {
    	Boolean result = RightAngleTriangle.rightAngleTriangle(3, 4, 5);
    	assertTrue(result);
    }
    @Test
    public void testNotRightAngleTriangle() {
    	Boolean result = RightAngleTriangle.rightAngleTriangle(1, 2, 3);
    	assertFalse(result);
    }
    @Test
    public void testEqualSides() {
    	Boolean result = RightAngleTriangle.rightAngleTriangle(2, 2, 2);
    	assertFalse(result);
    }
    @Test
    public void testRightAngleTriangleLargeNumbers() {
    	Boolean result = RightAngleTriangle.rightAngleTriangle(48, 55, 73);
    	assertTrue(result);
    }
    @Test
    public void testZeroOrNegativeInput() {
    	Boolean result = RightAngleTriangle.rightAngleTriangle(-1, 2, 3);
    	assertFalse(result);
    }
    @Test
    public void testTwoSidesEqualThirdSideDifferent() {
    	Boolean result = RightAngleTriangle.rightAngleTriangle(2, 2, 3);
    	assertFalse(result);
    }
    @Test
    public void testValidRightAngleTriangle() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(3, 4, 5));
    }
    @Test
    public void testInvalidRightAngleTriangle() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(1, 2, 3));
    }
    @Test
    public void testEqualSideLengths() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(2, 2, 2));
    }
    @Test
    public void testZeroSideLength() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(0, 1, 2));
    }
    @Test
    public void testSideLengthLessThanZero() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(-1, 1, 2));
    }
    @Test
    public void testIsoscelesTriangle() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(5, 5, 7));
    }
    @Test
    public void testNegativeInput() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(-1, 1, 2));
    }
    @Test
    public void testScaleneRightTriangle() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(3, 4, 5));
    }
    @Test
    public void testIsoscelesRightTriangle() {
        int c = (int) Math.round(Math.sqrt(2) * 5);
        assertFalse(RightAngleTriangle.rightAngleTriangle(5, 5, c));
    }
    @Test
    public void testEqualSides1() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(1, 1, 1));
    }
                                    
}