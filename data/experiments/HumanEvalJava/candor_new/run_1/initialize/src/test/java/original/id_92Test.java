package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AnyInt.
*/
class AnyIntTest {
    @Test
    void testAnyInt() {
        assertTrue(AnyInt.anyInt(2, 3, 1));
    }
}