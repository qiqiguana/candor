package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HexKey.
*/
class HexKeyTest {
    @Test
    void testHexKey_1() {
        String input = "AB";
        int expected = 1;
        int actual = HexKey.hexKey(input);
        assertEquals(expected, actual);
    }
}
