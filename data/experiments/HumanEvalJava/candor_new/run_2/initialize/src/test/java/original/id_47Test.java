package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Median.
*/
class MedianTest {
    @Test
    void testMedian_evenNumberList() {
        List<Integer> l = new ArrayList<>(List.of(1, 3));
        assertEquals(2.0, Median.median(l), "median should be calculated correctly for even number list");
    }
}