package oracle1;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
/**
* Test class of MeanAbsoluteDeviation.
*/
class MeanAbsoluteDeviationTest {
    @Test
    void testMeanAbsoluteDeviation() {
        List<Double> numbers = new ArrayList<>();
        numbers.add(1.0);
        numbers.add(2.0);
        numbers.add(3.0);
        Double expectedMad = 0.6666666666666666;
        assertEquals(expectedMad, MeanAbsoluteDeviation.meanAbsoluteDeviation(numbers));
    }
    
    @Test
        void testNothing(){
            MeanAbsoluteDeviation s = new MeanAbsoluteDeviation();
            }
    @Test
    public void testMeanAbsoluteDeviationWithPositiveNumbers() {
        List<Double> numbers = Arrays.asList(1.0, 2.0, 3.0);
        Double expected_result = 0.6666666666666666;
        assertEquals(expected_result, MeanAbsoluteDeviation.meanAbsoluteDeviation(numbers));
    }
                                    
}