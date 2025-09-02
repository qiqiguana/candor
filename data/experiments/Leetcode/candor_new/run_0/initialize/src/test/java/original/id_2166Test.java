package original;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Bitset.
*/
class BitsetTest {
    @Test
    void testAll() {
        // Arrange
        Bitset bitset = new Bitset(2);
        bitset.fix(0);
        bitset.fix(1);
        // Act & Assert
        assertTrue(bitset.all());
    }
}