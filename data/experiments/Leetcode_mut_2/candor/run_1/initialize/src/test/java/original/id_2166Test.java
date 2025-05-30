package original;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Bitset.
*/
class BitsetTest {
    @Test
    void testFixShouldIncrementCountWhenBitIsZero() {
        // Arrange
        int size = 5;
        Bitset bitset = new Bitset(size);
        int idx = 0;
        // Act
        bitset.fix(idx);
        // Assert
        assertEquals(1, bitset.count());
    }
}
