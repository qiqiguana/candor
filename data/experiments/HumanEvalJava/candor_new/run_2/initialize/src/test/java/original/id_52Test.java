package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of BelowThreshold.
*/
class BelowThresholdTest {

    @Test
    void testBelowThreshold_withNumbersBelowThreshold_returnsTrue() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(8);
        numbers.add(4);
        numbers.add(10);
        boolean result = BelowThreshold.belowThreshold(numbers, 11);
        assertTrue(result);
    }

}