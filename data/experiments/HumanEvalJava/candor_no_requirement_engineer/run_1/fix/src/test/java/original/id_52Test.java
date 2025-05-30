package original;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of BelowThreshold.
*/
class BelowThresholdTest {
    @Test
    void testBelowThreshold_AllNumbersBelowThreshold_ReturnsTrue() {
        List<Integer> numbers = List.of(1, 2, 4, 10);
        int threshold = 100;
        assertTrue(BelowThreshold.belowThreshold(numbers, threshold));
    }
    
    @Test
        void testNothing(){
            BelowThreshold s = new BelowThreshold();
            }
    @Test
    public void TestBelowThreshold_SadPath_NullList() {
        List<Integer> l = null;
        assertThrows(NullPointerException.class, () -> BelowThreshold.belowThreshold(l, 10));
    }
    @Test
    public void TestBelowThreshold_HappyPath_EmptyList() {
        List<Integer> l = new ArrayList<>();
        Boolean result = BelowThreshold.belowThreshold(l, 10);
        assertTrue(result);
    }
    @Test
    public void TestBelowThreshold_HappyPath_SingleElementBelowThreshold_1() {
        List<Integer> l = List.of(5);
        boolean result = BelowThreshold.belowThreshold(l, 10);
        assertTrue(result);
    }
    @Test
    public void BelowThreshold_HappyPath_MultipleElementsBelowThreshold_3() {
        List<Integer> numbers = Arrays.asList(1, 8, 4, 10);
        int threshold = 11;
        boolean result = BelowThreshold.belowThreshold(numbers, threshold);
        assertTrue(result);
    }
    @Test
    public void BelowThreshold_HappyPath_EmptyListAndZeroThreshold() {
        List<Integer> l = new ArrayList<>();
        boolean result = BelowThreshold.belowThreshold(l, 0);
        assertTrue(result);
    }
    @Test
    public void TestBelowThreshold_Behavior2() {
        List<Integer> l = Arrays.asList(15, 20);
        boolean result = BelowThreshold.belowThreshold(l, 10);
        assertFalse(result);
    }
                                    
}