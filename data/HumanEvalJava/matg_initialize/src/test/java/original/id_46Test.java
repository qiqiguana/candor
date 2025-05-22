package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Fib4.
*/
class Fib4Test {
    @Test
    void testFib4_baseCase() {
        assertEquals(0, Fib4.fib4(0));
    }
}