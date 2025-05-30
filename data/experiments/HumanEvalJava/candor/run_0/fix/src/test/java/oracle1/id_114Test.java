package oracle1;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Minsubarraysum.
*/
class MinsubarraysumTest {
    @Test
    void testMinSubArraySum() {
        List<Object> nums = new ArrayList<>();
        nums.add(-1);
        nums.add(-2);
        nums.add(-3);
        assertEquals(Minsubarraysum.minsubarraysum(nums), -6);
    }
    
    @Test
        void testNothing(){
            Minsubarraysum s = new Minsubarraysum();
            }
    @Test
    public void testMinSubArraySumWithNullInput() {
        List<Object> nums = null;
        assertThrows(Exception.class, () -> Minsubarraysum.minsubarraysum(nums));
    }
    @Test
    public void testMinSubArraySumWithPositiveNumbers1() {
    java.util.List<java.lang.Object> nums = java.util.Arrays.asList(2, 3, 4, 1, 2, 4);
    long result = oracle1.Minsubarraysum.minsubarraysum(nums);
    assertEquals(1, result);
    }
                                    
}