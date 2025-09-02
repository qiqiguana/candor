package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Minsubarraysum.
*/
class MinsubarraysumTest {
    @Test
    void testMinSubArraySum_withNegativeNumbers() {
        List<Object> nums = List.of(-1, -2, -3);
        long result = Minsubarraysum.minsubarraysum(nums);
        assertEquals(-6, result);
    }
}