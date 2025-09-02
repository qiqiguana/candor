package original;

import java.util.HashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Histogram.
*/
class HistogramTest {

    @Test
    void testHistogram() {
        HashMap<String, Integer> result = (HashMap<String, Integer>) Histogram.histogram("apple apple banana");
        assertNull(result);
    }

}