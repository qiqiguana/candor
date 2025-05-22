package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of BelowThreshold.
*/
class BelowThresholdTest {

    @Test
    void testBelowThreshold_AllNumbersBelowThreshold_ReturnsTrue() {
        List<Integer> numbers = List.of(1, 2, 4, 10);
        int threshold = 100;
        boolean result = BelowThreshold.belowThreshold(numbers, threshold);
        assertTrue(result);
    }

}