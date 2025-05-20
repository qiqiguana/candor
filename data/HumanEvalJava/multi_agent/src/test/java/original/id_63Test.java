package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Fibfib.
*/
class FibfibTest {
    @Test
    void testFibfibBaseCase() {
        assertEquals(0, Fibfib.fibfib(1));
    }
}