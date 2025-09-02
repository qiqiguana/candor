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
    void testMinSubArraySum_EmptyList_ReturnsZero() {
        List<Object> nums = new ArrayList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> Minsubarraysum.minsubarraysum(nums));
    }
    
    @Test
        public void testNothing(){
            Minsubarraysum s = new Minsubarraysum();
            }
    @Test
    public void TestMinSubArraySumWithNegativeNumbers() {
        List<Object> nums = new ArrayList<>();
        nums.add(new Long(-1));
        nums.add(new Long(-2));
        nums.add(new Long(-3));
        assertEquals(-6, Minsubarraysum.minsubarraysum(nums));
    }
    @Test
    public void TestMinSubArraySumWithMultiplePositiveNumbers() {
        List<Object> nums = new ArrayList<>();
        nums.add(new Long(1));
        nums.add(new Long(2));
        nums.add(new Long(3));
        assertEquals(1, Minsubarraysum.minsubarraysum(nums));
    }
                                    
}