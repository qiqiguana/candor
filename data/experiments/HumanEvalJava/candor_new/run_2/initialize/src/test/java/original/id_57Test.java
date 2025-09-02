package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Monotonic.
*/
class MonotonicTest {
    @Test
    void testMonotonicIncreasing() {
        List<Integer> l = List.of(1, 2, 4, 10);
        assertTrue(Monotonic.monotonic(l));
    }
}