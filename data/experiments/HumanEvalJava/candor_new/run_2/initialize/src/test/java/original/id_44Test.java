package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ChangeBase.
*/
class ChangeBaseTest {
    @Test
    void testChangeBase() {
        assertEquals("22", ChangeBase.changeBase(8, 3));
    }
}