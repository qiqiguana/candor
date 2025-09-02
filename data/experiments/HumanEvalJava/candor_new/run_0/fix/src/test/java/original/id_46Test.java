package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Fib4.
*/
class Fib4Test {
    @Test
    void testFib4() {
        assertEquals(104, Fib4.fib4(10));
    }
    
    @Test
        public void testNothing(){
            Fib4 s = new Fib4();
            }
    @Test
    public void testFib4_with_n_equals_0() {
        assertEquals(0, Fib4.fib4(0));
    }
    @Test
    public void testFib4_with_n_equals_2_corrected() {
        assertEquals(2, original.Fib4.fib4(2));
    }
                                    
}