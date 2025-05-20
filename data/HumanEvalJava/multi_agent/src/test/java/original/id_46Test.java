package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Fib4.
*/
class Fib4Test {
    @Test
    void testFib4_SimpleCase() {
        assertEquals(2, Fib4.fib4(2));
    }
}