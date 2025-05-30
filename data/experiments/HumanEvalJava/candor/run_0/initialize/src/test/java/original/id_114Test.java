package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Minsubarraysum.
*/
class MinsubarraysumTest {
    @Test
    void testMinSubArraySum() {
        List<Object> nums = List.of(2, 3, 4, 1, 2, 4);
        assertEquals(1, Minsubarraysum.minsubarraysum(nums));
    }
    
    @Test
        public void testNothing(){
            Minsubarraysum s = new Minsubarraysum();
            }
    @Test
    public void test_Minsubarraysum_NegativeNumbers() {
        List<Object> input = List.of(-1, -2, -3);
        long expected_result = -6;
        assertEquals(expected_result, Minsubarraysum.minsubarraysum(input));
    }
                                    
}