package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HexKey.
*/
class HexKeyTest {
    @Test
    void testHexKey_PrimeHexDigits_CountCorrect() {
        assertEquals(6, HexKey.hexKey("123456789ABCDEF0"));
    }
}