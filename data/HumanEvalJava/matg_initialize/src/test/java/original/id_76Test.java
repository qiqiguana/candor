package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsSimplePower.
*/
class IsSimplePowerTest {
    @Test
    void testIsSimplePower_1() {
        assertTrue(IsSimplePower.isSimplePower(16, 2));
    }
}