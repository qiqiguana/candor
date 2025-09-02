package original;

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

        List<Double> expected = new ArrayList<>();
        expected.add(0.0);
        expected.add(0.25);
        expected.add(0.5);
        expected.add(0.75);
        expected.add(1.0);

        assertEquals(expected, RescaleToUnit.rescaleToUnit(numbers));
    }
    
    @Test
        public void testNothing(){
            RescaleToUnit s = new RescaleToUnit();
            }
    @Test
    public void testRescaleToUnitWithEmptyList() {
        List<Double> numbers = new ArrayList<>();
        List<Double> result = RescaleToUnit.rescaleToUnit(numbers);
        assertEquals(0, result.size());
    }
    @Test
    public void testRescaleToUnitWithMinAndMaxAtTheBeginning() {
        List<Double> numbers = new ArrayList<>();
        numbers.add(1.0);
        numbers.add(5.0);
        numbers.add(3.0);
        numbers.add(4.0);
        List<Double> result = RescaleToUnit.rescaleToUnit(numbers);
        assertEquals(4, result.size());
        assertEquals(0.0, result.get(0), 0.01);
        assertEquals(1.0, result.get(1), 0.01);
        assertEquals(0.5, result.get(2), 0.01);
        assertEquals(0.75, result.get(3), 0.01);
    }
                                    
}