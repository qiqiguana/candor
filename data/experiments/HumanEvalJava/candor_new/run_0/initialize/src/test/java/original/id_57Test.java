package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
* Test class of Monotonic.
*/
class MonotonicTest {
    @Test
    void test_monotonic_increasing() {
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        assertTrue(Monotonic.monotonic(numbers));
    }
}