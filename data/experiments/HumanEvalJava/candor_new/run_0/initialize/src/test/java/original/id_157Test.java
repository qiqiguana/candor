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
}