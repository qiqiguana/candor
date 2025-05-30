package original;

import java.util.Arrays;
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
    
    @Test
        public void testNothing(){
            Minsubarraysum s = new Minsubarraysum();
            }
    @Test
    public void testMinSubArraySum_HappyPath_1() {
        List<Object> nums = Arrays.asList(2, 3, 4, -1, 2, 4);
        long expected = -1;
        assertEquals(expected, Minsubarraysum.minsubarraysum(nums));
    }
    @Test
    public void testMinSubArraySum_SadPath() {
        List<Object> nums = Arrays.asList(-1, -2, -3);
        long expected = -6;
        assertEquals(expected, Minsubarraysum.minsubarraysum(nums));
    }
    @Test
    public void testMinSubArraySum_Zero_1() {
        List<Object> nums = Arrays.asList(0, 10, 20, 1000000);
        long expected = 0;
        assertEquals(expected, Minsubarraysum.minsubarraysum(nums));
    }
    @Test
    public void testMinSubArraySum_LargeNumbers_1() {
        List<Object> nums = Arrays.asList(-1000000);
        long expected = -1000000;
        assertEquals(expected, Minsubarraysum.minsubarraysum(nums));
    }
    @Test
    public void testMinSubArraySum_SingleElement_1_Fixed() {
        List<Object> nums = Arrays.asList(5);
        long expected = 5;
        assertEquals(expected, Minsubarraysum.minsubarraysum(nums));
    }
    @Test
    public void testMinSubArraySum_MultipleElements_2() {
        List<Object> nums = Arrays.asList(1, 2, 3, 4);
        long expected = 1;
        assertEquals(expected, Minsubarraysum.minsubarraysum(nums));
    }
                                    
}