package original;

import java.util.Arrays;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of WillItFly.
*/
class WillItFlyTest {
    @Test
    void willItFly_BalancedAndWithinWeight_ReturnsTrue() {
        List<Integer> q = List.of(3, 2, 3);
        int w = 9;
        boolean result = WillItFly.willItFly(q, w);
        assertTrue(result);
    }
    
    @Test
        public void testNothing(){
            WillItFly s = new WillItFly();
            }
    @Test
    public void testWillItFly_UnbalancedList_Fix() {
        List<Integer> q = new ArrayList<>(Arrays.asList(1, 2));
        int w = 5;
        Boolean result = WillItFly.willItFly(q, w);
        assertFalse(result);
    }
    @Test
    public void willItFly_Balanced_and_exceeds_weight_limit() {
        List<Integer> q = new ArrayList<>(Arrays.asList(3, 2, 3));
        int w = 1;
        assertFalse(WillItFly.willItFly(q, w));
    }
                                    
}