package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HexKey.
*/
class HexKeyTest {
    @Test
    void testHexKey() {
        int result = HexKey.hexKey("AB");
        assertEquals(1, result);
    }
}