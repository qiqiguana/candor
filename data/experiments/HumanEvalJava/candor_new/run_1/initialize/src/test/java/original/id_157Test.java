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
}