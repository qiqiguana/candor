package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Bitset.
*/
class BitsetTest {
    @Test
    void testAllReturnsTrueWhenAllBitsAreSet() {
        // Arrange
        int size = 5;
        Bitset bitset = new Bitset(size);
        for (int i = 0; i < size; i++) {
            bitset.fix(i);
        }
        // Act and Assert
        assertTrue(bitset.all());
    }
}