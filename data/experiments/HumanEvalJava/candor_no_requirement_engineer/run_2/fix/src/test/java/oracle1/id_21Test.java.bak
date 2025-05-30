package oracle1;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RescaleToUnit.
*/
class RescaleToUnitTest {
    @Test
    void testRescaleToUnit()
    {
        List<Double> numbers = new ArrayList<>();
        numbers.add(1.0);
        numbers.add(2.0);
        numbers.add(3.0);
        numbers.add(4.0);
        numbers.add(5.0);
        List<Double> expected = new ArrayList<>();
        expected.add(0.0);
        expected.add(0.25);
        expected.add(0.5);
        expected.add(0.75);
        expected.add(1.0);
        assertEquals(expected, RescaleToUnit.rescaleToUnit(numbers));
    }
    
    @Test
        void testNothing(){
            RescaleToUnit s = new RescaleToUnit();
            }
    @Test
    public void testRescaleToUnit_NullList() {
        List<Double> numbers = null;
        assertThrows(NullPointerException.class, () -> RescaleToUnit.rescaleToUnit(numbers));
    }
    @Test
    public void testRescaleToUnit_AscendingOrder() {
        List<Double> numbers = Arrays.asList(2.0, 49.9);
        List<Double> expectedResults = Arrays.asList(0.0, 1.0);
        assertEquals(expectedResults, RescaleToUnit.rescaleToUnit(numbers));
    }
    @Test
    public void testRescaleToUnit_DescendingOrder_Fixed() {
        List<Double> numbers = Arrays.asList(100.0, 49.9);
        List<Double> expectedResults = Arrays.asList(1.0, 0.0);
        assertEquals(expectedResults, RescaleToUnit.rescaleToUnit(numbers));
    }
                                    
}