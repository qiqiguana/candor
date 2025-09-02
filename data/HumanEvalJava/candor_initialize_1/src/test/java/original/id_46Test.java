package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Fib4.
*/
class Fib4Test {

    @Test
    void testFib4BaseCase() {
        assertEquals(0, Fib4.fib4(1));
    }
    
    @Test
        public void testNothing(){
            Fib4 s = new Fib4();
            }
    @Test
    public void testFib4WithNEqualTo0() {
        assertEquals(0, Fib4.fib4(0));
    }
    @Test
    public void testFib4WithNEqualTo1() {
        assertEquals(0, Fib4.fib4(1));
    }
    @Test
    public void testFib4WithNEqualTo2() {
        assertEquals(2, Fib4.fib4(2));
    }
    @Test
    public void testFib4WithNEqualTo3() {
        assertEquals(0, Fib4.fib4(3));
    }
    @Test
    public void testFib4WithNEqualTo4() {
        assertEquals(2, Fib4.fib4(4));
    }
                                    
}