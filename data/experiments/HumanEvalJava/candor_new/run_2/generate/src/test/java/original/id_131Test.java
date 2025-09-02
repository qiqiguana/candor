package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Digits.
*/
class DigitsTest {
    @Test
    void testDigits_WhenAllDigitsAreEven_ReturnsZero() {
        // Arrange and Act
        int result = Digits.digits(2468);
        // Assert
        assertEquals(0, result);
    }
}