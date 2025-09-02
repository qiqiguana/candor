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
    void testMedian_oddSizeList() {
        List<Integer> list = new ArrayList<>(List.of(1, 3, 5));
        assertEquals(3, Median.median(list));
    }
    
    @Test
        public void testNothing(){
            Median s = new Median();
            }
                                    
}