package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of PrimeLength.
*/
class PrimeLengthTest {
    @Test
    void testPrimeLength_withStringLengthOf2_shouldReturnTrue() {
        assertTrue(PrimeLength.primeLength("HI"));
    }
}
