package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Fibfib.
*/
class FibfibTest {
    @Test
    void test_fibfib_2_should_be_equal_to_1() {
        int result = Fibfib.fibfib(2);
        assertEquals(1, result);
    }
    
    @Test
        public void testNothing(){
            Fibfib s = new Fibfib();
            }
    @Test
    public void testFibfib_n_0() {
        int n = 0;
        int result = original.Fibfib.fibfib(n);
        assertEquals(0, result);
    }
    @Test
    public void testFibfib_n_gt_2() {
        int n = 3;
        int result = original.Fibfib.fibfib(n);
        assertEquals(1, result);
    }
                                    
}