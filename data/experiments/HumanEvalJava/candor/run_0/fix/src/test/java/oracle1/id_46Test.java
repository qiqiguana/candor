package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Fib4.
*/
class Fib4Test {
    @Test
    void testFib4() {
        assertEquals(386, Fib4.fib4(12));
    }
    
    @Test
        void testNothing(){
            Fib4 s = new Fib4();
            }
    @Test
    public void testFib4_with_n_less_than_2() {
        assertEquals(0, oracle1.Fib4.fib4(1));
    }
    @Test
    public void testFib4_with_n_equal_to_2() {
        assertEquals(2, oracle1.Fib4.fib4(2));
    }
    @Test
    public void testFib4_with_n_greater_than_2() {
        assertEquals(4, oracle1.Fib4.fib4(5));
    }
    @Test
    public void testFib4_edge_case_with_n_equal_to_0() {
        assertEquals(0, oracle1.Fib4.fib4(0));
    }
    @Test
    public void testFib4_edge_case_with_large_value_of_n() {
        assertEquals(386, oracle1.Fib4.fib4(12));
    }
                                    
}