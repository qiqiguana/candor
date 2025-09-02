package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsHappy.
*/
class IsHappyTest {
    @Test
    void testIsHappy_FalseReturned_ForLessThanThreeChars() {
        // Arrange
        String s = "ab";
        // Act
        Boolean result = IsHappy.isHappy(s);
        // Assert
        assertFalse(result);
    }
}