package original;

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
        double expectedMAD = 0.6666666666666666;
        assertEquals(expectedMAD, MeanAbsoluteDeviation.meanAbsoluteDeviation(numbers), 1e-9);
    }
    
    @Test
        public void testNothing(){
            MeanAbsoluteDeviation s = new MeanAbsoluteDeviation();
            }
                                    
}