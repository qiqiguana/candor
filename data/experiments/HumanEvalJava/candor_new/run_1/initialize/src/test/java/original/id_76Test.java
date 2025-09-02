package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsSimplePower.
*/
class IsSimplePowerTest {
    @Test
    void testIsSimplePowerShouldReturnTrueForXEqualTo1() {
        // Arrange and Act
        boolean result = IsSimplePower.isSimplePower(1, 12);
        // Assert
        assertTrue(result);
    }
}