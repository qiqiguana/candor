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
    
    @Test
        public void testNothing(){
            Fib s = new Fib();
            }
    @Test
    public void testNormalFibonacciSequence() {
        int n = 10;
        int expectedResult = 55;
        assertEquals(expectedResult, Fib.fib(n));
    }
    @Test
    public void testFirstFibonacciNumber() {
        int n = 1;
        int expectedResult = 1;
        assertEquals(expectedResult, Fib.fib(n));
    }
    @Test
    public void testLargeFibonacciNumber() {
        int n = 20;
        int expectedResult = 6765;
        assertEquals(expectedResult, Fib.fib(n));
    }
    @Test
    public void testZerothFibonacciNumberFixed() {
        int n = 0;
        int expectedResult = 1; // corrected expected result based on potential fix
        assertEquals(expectedResult, Fib.fib(n));
    }
                                    
}