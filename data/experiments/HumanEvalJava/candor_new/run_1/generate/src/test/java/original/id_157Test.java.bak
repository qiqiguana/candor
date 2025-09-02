package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RightAngleTriangle.
*/
class RightAngleTriangleTest {
    @Test
    void testRightAngleTriangle_RightAngle_ReturnsTrue() {
        // Given
        int a = 3;
        int b = 4;
        int c = 5;
        
        // When
        boolean result = RightAngleTriangle.rightAngleTriangle(a, b, c);
        
        // Then
        assertTrue(result);
    }
    
    @Test
        public void testNothing(){
            RightAngleTriangle s = new RightAngleTriangle();
            }
    @Test
    public void testRightAngleTriangleWithEqualSides() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(1, 1, 1));
    }
    @Test
    public void testRightAngleTriangleWithAGreaterThanBAndAGreaterThanC() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(5, 3, 4));
    }
    @Test
    public void testRightAngleTriangleWithBGreaterThanAAndBGreaterThanC() {
        assertTrue(RightAngleTriangle.rightAngleTriangle(3, 5, 4));
    }
    @Test
    public void testRightAngleTriangle_IncreasingOrder_False() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(1, 2, 3));
    }
    @Test
    public void testRightAngleTriangle_DecreasingOrder_False() {
        assertFalse(RightAngleTriangle.rightAngleTriangle(10, 6, 5));
    }
    @Test
    public void testRightAngleTriangle_IncreasingOrder_RightAngle_3() {
        int[] sides = {3, 4, 5};
        boolean actualResult = RightAngleTriangle.rightAngleTriangle(sides[0], sides[1], sides[2]);
        boolean expectedResult = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if (i != j && j != k && k != i) {
                        boolean permutedResult = RightAngleTriangle.rightAngleTriangle(sides[i], sides[j], sides[k]);
                        assertEquals(expectedResult, permutedResult);
                    }
                }
            }
        }
    }
                                    
}