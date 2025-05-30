package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of PrimeFib.
*/
class PrimeFibTest {
    @Test
    void testPrimeFib_ReturnsCorrectResult_WhenInputIsValid() {
        int result = PrimeFib.primeFib(5);
        assertEquals(89, result);
    }
}