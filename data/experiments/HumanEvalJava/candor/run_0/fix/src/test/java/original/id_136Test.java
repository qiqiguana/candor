package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestSmallestIntegers.
*/
class LargestSmallestIntegersTest {
    @Test
    void test_largestSmallestIntegers_with_positive_and_negative_numbers() {
        List<Object> input = new ArrayList<>();
        input.add(2);
        input.add(4);
        input.add(1);
        input.add(3);
        input.add(5);
        input.add(-7);

        List<Integer> expected = new ArrayList<>();
        expected.add(-7);
        expected.add(1);

        assertEquals(expected, LargestSmallestIntegers.largestSmallestIntegers(input));
    }
    
    @Test
     void testNothing(){
         LargestSmallestIntegers s = new LargestSmallestIntegers();
         }
    @Test
    public void testEmptyList() {
        List<Object> lst = new ArrayList<>();
        assertEquals(Arrays.asList(null, null), LargestSmallestIntegers.largestSmallestIntegers(lst));
    }
                                  
}