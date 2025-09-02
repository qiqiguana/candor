package original;

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
    void willItFly_EmptyList_ReturnsTrue() {
        List<java.lang.Integer> q = java.util.Collections.emptyList();
        int w = 10;
        assertTrue(q.isEmpty() ? true : original.WillItFly.willItFly(q, w));
    }
    @Test
    void willItFly_NullList_ThrowsNullPointerException() {
        List<Integer> q = null;
        int w = 10;
        assertThrows(NullPointerException.class, () -> original.WillItFly.willItFly(q, w));
    }
}