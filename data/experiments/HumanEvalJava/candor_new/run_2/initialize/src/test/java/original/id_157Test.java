package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RightAngleTriangle.
*/
class RightAngleTriangleTest {

    @Test
    void testRightAngleTriangle_3_4_5_ReturnsTrue() {
        // Arrange and Act
        boolean result = RightAngleTriangle.rightAngleTriangle(3, 4, 5);

        // Assert
        assertTrue(result);
    }
}