package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Fib.
*/
class FibTest {
    @Test
    void testFibFunction() {
        assertEquals(55, Fib.fib(10));
    }
}