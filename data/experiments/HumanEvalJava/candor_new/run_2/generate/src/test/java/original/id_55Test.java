package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Fib.
*/
class FibTest {
    @Test
    void testFibonacci() {
        int n = 10;
        int expected = 55;
        int actual = Fib.fib(n);
        assertEquals(expected, actual);
    }
}
