package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HexKey.
*/
class HexKeyTest {
    @Test
    void testHexKey() {
        String num = "AB";
        int expected = 1;
        int actual = HexKey.hexKey(num);
        assertEquals(expected, actual);
    }
}