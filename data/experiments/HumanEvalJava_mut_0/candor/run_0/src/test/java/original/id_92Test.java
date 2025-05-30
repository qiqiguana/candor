package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AnyInt.
*/
class AnyIntTest {
    @Test
    void testAnyInt_withValidInput_ReturnsTrue() {
        // Arrange
        Number x = 10;
        Number y = 5;
        Number z = 5;

        // Act
        Boolean result = AnyInt.anyInt(x, y, z);

        // Assert
        assertTrue(result);
    }
}