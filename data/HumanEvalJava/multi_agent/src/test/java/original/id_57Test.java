package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Monotonic.
*/
class MonotonicTest {
    @Test
    void testMonotonic_IncreasingList_ReturnsTrue() {
        List<Integer> l = new ArrayList<>();
        l.add(1);
        l.add(2);
        l.add(4);
        l.add(10);
        assertTrue(Monotonic.monotonic(l));
    }
}