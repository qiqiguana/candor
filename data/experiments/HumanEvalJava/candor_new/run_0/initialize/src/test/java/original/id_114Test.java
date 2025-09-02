package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
* Test class of Minsubarraysum.
*/
class MinsubarraysumTest {

    @Test
    void testMinSubArraySum() {
        List<Object> nums = new ArrayList<>();
        nums.add(2);
        nums.add(3);
        nums.add(4);
        nums.add(1);
        nums.add(2);
        nums.add(4);
        long result = Minsubarraysum.minsubarraysum(nums);
        assertEquals(1, result);
    }
}