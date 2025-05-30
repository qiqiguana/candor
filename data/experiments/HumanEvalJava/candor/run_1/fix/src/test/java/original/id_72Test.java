package original;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of WillItFly.
*/
class WillItFlyTest {
    @Test
    void testWillItFly_BalancedAndSumLessThanMaxWeight_ReturnsTrue() {
        List<Integer> q = List.of(3, 2, 3);
        int w = 9;
        Boolean result = WillItFly.willItFly(q, w);
        assertTrue(result);
    }
    
    @Test
     void testNothing(){
         WillItFly s = new WillItFly();
         }
    @Test
    public void TestWillItFly_BalancedListLessThanMaxWeight() {
        List<Integer> q = Arrays.asList(3, 2, 3);
        int w = 9;
        assertTrue(WillItFly.willItFly(q, w));
    }
    @Test
    public void TestWillItFly_UnbalancedListLessThanMaxWeight() {
        List<Integer> q = Arrays.asList(1, 2);
        int w = 5;
        assertFalse(WillItFly.willItFly(q, w));
    }
    @Test
    public void TestWillItFly_BalancedListMoreThanMaxWeight() {
        List<Integer> q = Arrays.asList(3, 2, 3);
        int w = 1;
        assertFalse(WillItFly.willItFly(q, w));
    }
                                  
}