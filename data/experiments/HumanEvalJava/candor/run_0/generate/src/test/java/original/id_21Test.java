package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import original.RescaleToUnit;
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
    void testRescaleToUnit_SimpleCase() {
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
    void testRescaleToUnitWithPositiveNumbers() {
      List<Double> input = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
      List<Double> expectedOutput = Arrays.asList(0.0, 0.25, 0.5, 0.75, 1.0);
      assertEquals(expectedOutput, RescaleToUnit.rescaleToUnit(input));
    }
    @Test
    void testRescaleToUnitWithDuplicateNumbers1() {
        List<Double> input = Arrays.asList(1.0, 1.0, 2.0, 3.0, 3.0);
        List<Double> expectedOutput = Arrays.asList(0.0, 0.0, 0.5, 1.0, 1.0);
        assertEquals(expectedOutput, RescaleToUnit.rescaleToUnit(input));
    }
    @Test
    void testRescaleToUnitWithLargeNumbers1() {
        List<Double> input = Arrays.asList(1000000.0, 2000000.0, 3000000.0, 4000000.0, 5000000.0);
        List<Double> expectedOutput = Arrays.asList(0.0, 0.25, 0.5, 0.75, 1.0);
        assertEquals(expectedOutput, RescaleToUnit.rescaleToUnit(input));
    }
    @Test
    public void rescaleToUnit_Doubles() {
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
    void testRescaleToUnitWithTwoNumbersCorrected() {
        List<Double> input = Arrays.asList(1.0, 2.0);
        List<Double> expectedOutput = Arrays.asList(0.0, 1.0);
        assertEquals(expectedOutput, RescaleToUnit.rescaleToUnit(input));
    }
    @Test
    void testRescaleToUnitWithThreeNumbers() {
        List<Double> input = Arrays.asList(1.0, 2.0, 3.0);
        List<Double> expectedOutput = Arrays.asList(0.0, 0.5, 1.0);
        assertEquals(expectedOutput, RescaleToUnit.rescaleToUnit(input));
    }
    @Test
    void testRescaleToUnitWithFourNumbers() {
        List<Double> input = Arrays.asList(1.0, 2.0, 3.0, 4.0);
        List<Double> expectedOutput = Arrays.asList(0.0, 0.3333333333333333, 0.6666666666666666, 1.0);
        assertEquals(expectedOutput, RescaleToUnit.rescaleToUnit(input));
    }
    @Test
    void testRescaleToUnitWithFiveNumbers() {
      List<Double> input = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
      List<Double> expectedOutput = Arrays.asList(0.0, 0.25, 0.5, 0.75, 1.0);
      assertEquals(expectedOutput, RescaleToUnit.rescaleToUnit(input));
    }
                                    
}