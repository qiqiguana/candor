package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HexKey.
*/
class HexKeyTest {
    @Test
    void testHexKey_PrimeDigitsInGivenString_ReturnsCount() {
        // Arrange
        String num = "ABED1A33";
        int expected = 4;
        // Act
        int actual = HexKey.hexKey(num);
        // Assert
        assertEquals(expected, actual);
    }
}