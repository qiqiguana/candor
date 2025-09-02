package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RoundedAvg.
*/
class RoundedAvgTest {
    @Test
    void testRoundedAvg_ReturnsMinusOne_WhenNIsGreaterThanM() {
        Object result = RoundedAvg.roundedAvg(7, 5);
        assertEquals(-1, result);
    }
    
    @Test
        public void testNothing(){
            RoundedAvg s = new RoundedAvg();
            }
    @Test
    public void TestRoundedAvg_3() {
    	Object result = RoundedAvg.roundedAvg(1, 100);
    	assertEquals("0b110010", result);
    }
                                    
}