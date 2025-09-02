package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of BelowThreshold.
*/
class BelowThresholdTest {
    @Test
    void testBelowThreshold_ReturnsTrue_WhenAllNumbersAreBelowThreshold() {
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 4, 10));
        int threshold = 100;
        boolean result = BelowThreshold.belowThreshold(numbers, threshold);
        assertTrue(result);
    }
    
    @Test
        public void testNothing(){
            BelowThreshold s = new BelowThreshold();
            }
    @Test
    public void belowThreshold_singleElementList_atThreshold() {
        List<Integer> l = new ArrayList<>();
        l.add(5);
        boolean result = BelowThreshold.belowThreshold(l, 5);
        assertEquals(false, result);
    }
                                    
}