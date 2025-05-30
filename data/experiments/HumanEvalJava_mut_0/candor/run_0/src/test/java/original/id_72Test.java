package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of WillItFly.
*/
class WillItFlyTest {
    @Test
    void testWillItFly_SymmetricAndSumLessThanW_ReturnsTrue() {
        List<Integer> q = List.of(1, 2, 3, 2, 1);
        int w = 10;
        assertTrue(WillItFly.willItFly(q, w));
    }
}