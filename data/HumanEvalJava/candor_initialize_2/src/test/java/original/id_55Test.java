package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Fib.
*/
class FibTest {
    @Test
    void testFibonacciNumberForSmallInput() {
        assertEquals(1, Fib.fib(1));
    }
    
    @Test
        public void testNothing(){
            Fib s = new Fib();
            }
    @Test
    public void TestFibClassInitialization() {
        // Verify no exception is thrown when initializing the Fib class
        assertDoesNotThrow(() -> new Fib());
    }
    @Test
    public void TestFibLoopIteration() {
        // Verify the result of the fib method for n = 10
        assertEquals(55, Fib.fib(10));
    }
                                    
}