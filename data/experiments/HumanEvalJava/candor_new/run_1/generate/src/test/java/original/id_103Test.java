package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RoundedAvg.
*/
class RoundedAvgTest {
    @Test
    void testRoundedAvg_NIsGreaterThanM_ReturnMinusOne() {
        assertEquals(-1, RoundedAvg.roundedAvg(7, 5));
    }
    
    @Test
        public void testNothing(){
            RoundedAvg s = new RoundedAvg();
            }
    @Test
    public void testRoundedAvg_n_equals_m() {
        Object result = RoundedAvg.roundedAvg(5, 5);
        assertEquals("0b101", result);
    }
                                    
}