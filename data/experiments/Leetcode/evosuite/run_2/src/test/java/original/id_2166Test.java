package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Bitset.
*/
class BitsetTest {
    @Test
    void testCountAfterFix() {
        // Arrange
        Bitset bitset = new Bitset(5);

        // Act
        bitset.fix(0);
        bitset.fix(1);
        
        // Assert
        assertEquals(2, bitset.count());
    }
}