package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Minsubarraysum.
*/
class MinsubarraysumTest {
    @Test
    void testMinSubArraySum_SingleElement_ReturnsElement() {
        List<Object> nums = List.of(-10);
        long expected = -10;
        assertEquals(expected, Minsubarraysum.minsubarraysum(nums));
    }
}