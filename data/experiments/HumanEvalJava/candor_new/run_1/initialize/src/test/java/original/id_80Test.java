package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsHappy.
*/
class IsHappyTest {
    @Test
    void testIsHappyReturnsFalseForStringsWithLessThan3Characters() {
        // Arrange and Act
        Boolean result = IsHappy.isHappy("a");
        // Assert
        assertFalse(result);
    }
}
