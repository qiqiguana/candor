package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of WillItFly.
*/
class WillItFlyTest {
    @Test
    void willItFly_BalancedSumLessThanWeight_ReturnsTrue() {
        List<Integer> q = List.of(3, 2, 3);
        int w = 9;
        assertTrue(WillItFly.willItFly(q, w));
    }
    
    @Test
        public void testNothing(){
            WillItFly s = new WillItFly();
            }
                                    
}