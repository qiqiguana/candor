package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Modp.
*/
class ModpTest {
    @Test
    void testModp() {
        int result = Modp.modp(2, 5);
        assertEquals(3, result);
    }
}