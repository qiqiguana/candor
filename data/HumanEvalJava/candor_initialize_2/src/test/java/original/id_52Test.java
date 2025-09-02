package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of BelowThreshold.
*/
class BelowThresholdTest {
    @Test
    void testBelowThreshold_allNumbersBelowThreshold() {
        List<Integer> numbers = List.of(1, 2, 4, 10);
        int threshold = 100;
        assertTrue(BelowThreshold.belowThreshold(numbers, threshold));
    }
    
    @Test
        public void testNothing(){
            BelowThreshold s = new BelowThreshold();
            }
    @Test
    public void testBelowThreshold_WithThresholdExceeded() {
    	List<Integer> numbers = List.of(1, 20, 4, 10);
    	int threshold = 5;
    	assertFalse(BelowThreshold.belowThreshold(numbers, threshold));
    }
                                    
}