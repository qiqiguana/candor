package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RoundedAvg.
*/
class RoundedAvgTest {
    @Test
    void testRoundedAvg_NIsGreaterThanM_ReturnsMinusOne() {
        assertEquals(-1, RoundedAvg.roundedAvg(7, 5));
    }
    
    @Test
        public void testNothing(){
            RoundedAvg s = new RoundedAvg();
            }
    @Test
    public void TestRoundedAvg_with_n_less_than_m() {
    	Object result = RoundedAvg.roundedAvg(1, 5);
    	assertEquals("0b11", result.toString());
    }
                                    
}