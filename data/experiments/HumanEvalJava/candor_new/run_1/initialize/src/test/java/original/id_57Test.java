package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Monotonic.
*/
class MonotonicTest {
    @Test
    void testMonotonicDecreasingList() {
        List<Integer> l = List.of(4, 1, 0, -10);
        assertTrue(Monotonic.monotonic(l));
    }
}