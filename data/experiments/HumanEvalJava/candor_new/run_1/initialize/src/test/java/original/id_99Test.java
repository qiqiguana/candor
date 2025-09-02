package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ClosestInteger.
*/
class ClosestIntegerTest {
    @Test
    void testClosestInteger() {
        assertEquals(15, ClosestInteger.closestInteger("14.5"));
    }
}
