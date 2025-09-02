package original;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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
        assertEquals(0.6666666666666666, MeanAbsoluteDeviation.meanAbsoluteDeviation(numbers), 0.000001);
    }
    
    @Test
        public void testNothing(){
            MeanAbsoluteDeviation s = new MeanAbsoluteDeviation();
            }
                                    
}