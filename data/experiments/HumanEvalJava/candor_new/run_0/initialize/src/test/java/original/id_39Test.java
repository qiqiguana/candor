package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of PrimeFib.
*/
class PrimeFibTest {
    @Test
    void primeFib_FirstCall_Returns2() {
        assertEquals(2, PrimeFib.primeFib(1));
    }
}