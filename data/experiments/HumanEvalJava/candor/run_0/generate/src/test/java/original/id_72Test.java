package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of WillItFly.
*/
class WillItFlyTest {
    @Test
    void willItFly_BalancedAndLessThanMaxWeight_ReturnsTrue() {
        List<Integer> q = List.of(3, 2, 3);
        int w = 9;
        assertTrue(WillItFly.willItFly(q, w));
    }
    
    @Test
        public void testNothing(){
            WillItFly s = new WillItFly();
            }
    @Test
    public void willItFly_NullList_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> WillItFly.willItFly(null, 10));
    }
    @Test
    public void willItFly_EmptyList_ReturnsTrue() {
        assertTrue(WillItFly.willItFly(new ArrayList<>(), 10));
    }
    @Test
    public void willItFly_UnbalancedList_ReturnsFalse() {
        assertFalse(WillItFly.willItFly(Arrays.asList(1, 2), 10));
    }
    @Test
    public void willItFly_EmptyList_ReturnsFalse_3() {
        assertFalse(WillItFly.willItFly(Arrays.asList(), -10));
    }
    @Test
    public void willItFly_NegativeWeight_ReturnsFalse() {
        assertFalse(WillItFly.willItFly(List.of(1, 2), -10));
    }
    @Test
    public void willItFly_ValidInput_ReturnsTrue_1() {
        List<Integer> q = Arrays.asList(3, 2, 3);
        int w = 9;
        assertTrue(WillItFly.willItFly(q, w));
    }
    @Test
    public void willItFly_ValidWeight_ReturnsExpectedResult() {
        List<Integer> q = Arrays.asList(3, 2, 3);
        int w = 9;
        assertTrue(WillItFly.willItFly(q, w));
    }
                                    
}