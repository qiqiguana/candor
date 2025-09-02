package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Fibfib.
*/
class FibfibTest {
    @Test
    void testFibfibBaseCases() {
        assertEquals(0, Fibfib.fibfib(0));
        assertEquals(0, Fibfib.fibfib(1));
        assertEquals(1, Fibfib.fibfib(2));
    }
    
    @Test
        public void testNothing(){
            Fibfib s = new Fibfib();
            }
    @Test
    public void test_fibfib_with_n_equal_3() {
        assertEquals(1, original.Fibfib.fibfib(3));
    }
                                    
}