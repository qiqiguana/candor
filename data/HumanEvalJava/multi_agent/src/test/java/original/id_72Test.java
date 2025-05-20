package original;

import java.util.Arrays;
import java.util.List; import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List; import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.List; import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of WillItFly.
*/
class WillItFlyTest {
    @Test
    void testWillItFly_BalancedAndWithinWeight() {
        List<Integer> q = List.of(3, 2, 3);
        int w = 9;
        assertTrue(WillItFly.willItFly(q, w));
    }
    @Test
    public void test_Balanced_List() {
        List<Integer> q = List.of(3, 2, 3);
        int w = 9;
        assertTrue(WillItFly.willItFly(q, w));
    }
    @Test
    public void test_Unbalanced_List() {
        List<Integer> q = List.of(1, 2);
        int w = 5;
        assertFalse(WillItFly.willItFly(q, w));
    }
    @Test
    public void test_Weight_Exceeded() {
        List<Integer> q = List.of(3, 2, 3);
        int w = 1;
        assertFalse(WillItFly.willItFly(q, w));
    }
    @Test
    public void test_Empty_List() {
        List<Integer> q = List.of();
        int w = 5;
        assertTrue(WillItFly.willItFly(q, w));
    }
    @Test
    public void test_Null_Input() {
        List<Integer> q = null;
        int w = 5;
        assertThrows(NullPointerException.class, () -> WillItFly.willItFly(q, w));
    }
    @Test
    public void willItFlyTest() {
        List<Integer> q = Arrays.asList(3, 2, 3);
        int w = 9;
        assertTrue(original.WillItFly.willItFly(q, w));
    }
    public void willItFly_UnbalancedList_WithinWeightLimit_ReturnsFalse() { List<Integer> q = Arrays.asList(1, 2); int w = 10; assertFalse(WillItFly.willItFly(q, w)); }
    public void willItFly_SingleElementList_ReturnsTrue() { List<Integer> q = Arrays.asList(5); int w = 5; assertTrue(WillItFly.willItFly(q, w)); }
    public void willItFly_BalancedList_ExceedsWeightLimit_ReturnsFalse() { List<Integer> q = Arrays.asList(3, 2, 3); int w = 1; assertFalse(WillItFly.willItFly(q, w)); }
    public void willItFly_BalancedList_WithinWeightLimit_ReturnsTrue() { List<Integer> q = Arrays.asList(3, 2, 3); int w = 9; assertTrue(WillItFly.willItFly(q, w)); }
    public void willItFly_UnbalancedList_ReturnsFalse() { List<Integer> q = Arrays.asList(1, 2); int w = 10; assertFalse(WillItFly.willItFly(q, w)); }
    public void willItFly_NullList_ThrowsNullPointerException() { List<Integer> q = null; int w = 10; assertThrows(NullPointerException.class, () -> WillItFly.willItFly(q, w)); }
}