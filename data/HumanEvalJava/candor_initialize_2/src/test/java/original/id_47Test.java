package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Median.
*/
class MedianTest {
    @Test
    void testMedian_withEvenNumberOfElements_returnsAverageOfMiddleTwo() {
        List<Integer> numbers = new ArrayList<>(List.of(1, 3, 5, 7));
        double expectedMedian = (5 + 3) / 2.0;
        assertEquals(expectedMedian, Median.median(numbers));
    }
    
    @Test
        public void testNothing(){
            Median s = new Median();
            }
    @Test
    public void test_MedianOfOddSizedListWithOneElement() {
        List<Integer> l = new ArrayList<>(Collections.singletonList(5));
        assertEquals(5, original.Median.median(l));
    }
                                    
}