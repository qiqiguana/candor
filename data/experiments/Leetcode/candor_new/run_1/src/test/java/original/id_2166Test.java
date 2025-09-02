package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Bitset.
*/
class BitsetTest {
    @Test
    void testFlip() {
        Bitset bitset = new Bitset(5);
        bitset.fix(0);
        bitset.flip();
        assertEquals("01111", bitset.toString());
    }
}