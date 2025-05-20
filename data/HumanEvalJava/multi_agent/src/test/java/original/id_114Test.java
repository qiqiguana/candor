package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Minsubarraysum.
*/
class MinsubarraysumTest {

    @Test
    void testMinSubArraySum_SimpleCase() {
        List<Object> nums = List.of(2, 3, 4, 1, 2, 4);
        long expected = 1;
        assertEquals(expected, Minsubarraysum.minsubarraysum(nums));
    }

}