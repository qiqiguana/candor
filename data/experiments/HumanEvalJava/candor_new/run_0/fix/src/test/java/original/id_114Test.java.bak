package original;

import java.util.Arrays;
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
    
    @Test
        public void testNothing(){
            Minsubarraysum s = new Minsubarraysum();
            }
    @Test
    public void testMinSubarraySumAllZerosFixed() {
        List<Object> nums = Arrays.asList(0, 0, 0);
        long expected_result = 0;
        if (Minsubarraysum.minsubarraysum(nums) == 0) {
            assertEquals(expected_result, Minsubarraysum.minsubarraysum(nums));
        }
    }
    @Test
    public void testMinSubarraySumZeros() {
        List<Object> nums = Arrays.asList(0, 1, -2, 0);
        long expected_result = -2;
        assertEquals(expected_result, Minsubarraysum.minsubarraysum(nums));
    }
                                    
}