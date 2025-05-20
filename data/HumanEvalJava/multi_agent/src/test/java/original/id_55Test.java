package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Fib.
*/
class FibTest {
    @Test
    void testFibonacciNumberAtPosition1() {
        assertEquals(1, Fib.fib(1));
    }
}