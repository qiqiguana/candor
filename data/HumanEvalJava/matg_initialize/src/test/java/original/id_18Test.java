package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HowManyTimes.
*/
class HowManyTimesTest {
    @Test
    void testHowManyTimes_EmptyString_ReturnsZero() {
        // Arrange and Act
        int result = HowManyTimes.howManyTimes("", "a");
        // Assert
        assertEquals(0, result);
    }
}