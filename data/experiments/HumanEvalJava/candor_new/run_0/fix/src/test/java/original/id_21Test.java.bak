package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RescaleToUnit.
*/
class RescaleToUnitTest {
    @Test
    void testRescaleToUnit() {
        List<Double> numbers = new ArrayList<>();
        numbers.add(1.0);
        numbers.add(2.0);
        numbers.add(3.0);
        numbers.add(4.0);
        numbers.add(5.0);
        List<Double> result = RescaleToUnit.rescaleToUnit(numbers);
        assertEquals(0.0, result.get(0), 1e-6);
    }
    
    @Test
        public void testNothing(){
            RescaleToUnit s = new RescaleToUnit();
            }
    @Test
    public void testRescaleToUnit_MinAndMaxValuesInMiddle_1() {
        List<Double> input = new ArrayList<>(Arrays.asList(3.0, 1.0, 5.0, 2.0));
        List<Double> expectedOutput = new ArrayList<>(Arrays.asList(0.5, 0.0, 1.0, 0.25));
        assertEquals(expectedOutput, RescaleToUnit.rescaleToUnit(input));
    }
                                    
}