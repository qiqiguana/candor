package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of MeanAbsoluteDeviation.
*/
class MeanAbsoluteDeviationTest {
    @Test
    void testMeanAbsoluteDeviation() {
        List<Double> numbers = List.of(1.0, 2.0, 3.0);
        Double expected = 0.6666666666666666;
        assertEquals(expected, MeanAbsoluteDeviation.meanAbsoluteDeviation(numbers));
    }
}