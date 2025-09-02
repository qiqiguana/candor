package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestDivisor.
*/
class LargestDivisorTest {
    @Test
    void testLargestDivisor_ReturnsCorrectResult() {
        // Arrange and Act
        int result = LargestDivisor.largestDivisor(15);
        // Assert
        assertEquals(5, result);
    }
}