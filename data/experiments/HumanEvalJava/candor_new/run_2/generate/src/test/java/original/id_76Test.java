package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsSimplePower.
*/
class IsSimplePowerTest {
    @Test
    void testIsSimplePower_8_2_ReturnsTrue() {
        // Arrange and Act
        boolean result = IsSimplePower.isSimplePower(8, 2);
        // Assert
        assertTrue(result);
    }
}
