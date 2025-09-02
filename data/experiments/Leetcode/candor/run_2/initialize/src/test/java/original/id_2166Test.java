package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Bitset.
*/
class BitsetTest {
    @Test
    void testFix() {
        // Arrange
        Bitset bitset = new Bitset(5);

        // Act
        bitset.fix(2);

        // Assert
        assertEquals("00100", bitset.toString());
    }
}