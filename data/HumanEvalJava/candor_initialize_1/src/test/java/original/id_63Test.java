package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Fibfib.
*/
class FibfibTest {
    @Test
    void test_fibfib_8() {
        assertEquals(24, Fibfib.fibfib(8));
    }
    
    @Test
        public void testNothing(){
            Fibfib s = new Fibfib();
            }
    @Test
    public void test_fibfib_with_n_0() {
        int result = Fibfib.fibfib(0);
        assertEquals(0, result);
    }
    @Test
    public void test_fibfib_with_n_1() {
        int result = Fibfib.fibfib(1);
        assertEquals(0, result);
    }
    @Test
    public void test_fibfib_with_n_2() {
        int result = Fibfib.fibfib(2);
        assertEquals(1, result);
    }
                                    
}