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
void testRescaleToUnitShouldReturnNormalizedValues() {
	List<Double> numbers = new ArrayList<>(List.of(1.0, 2.0, 3.0, 4.0, 5.0));
	List<Double> expectedResults = new ArrayList<>(List.of(0.0, 0.25, 0.5, 0.75, 1.0));
	assertEquals(expectedResults, RescaleToUnit.rescaleToUnit(numbers));
}

@Test
    public void testNothing(){
        RescaleToUnit s = new RescaleToUnit();
        }
@Test
public void testRescaleToUnitWithMaxAndMinEqual2() {
    List<Double> numbers = new ArrayList<>();
    numbers.add(5.0);
    numbers.add(5.0);
    numbers.add(5.0);
    numbers.add(5.0);
    numbers.add(5.0);
    List<Double> expected = new ArrayList<>();
    expected.add(Double.NaN);
    expected.add(Double.NaN);
    expected.add(Double.NaN);
    expected.add(Double.NaN);
    expected.add(Double.NaN);
    try {
        RescaleToUnit.rescaleToUnit(numbers);
    } catch (ArithmeticException e) {
        assertEquals("All numbers are equal", e.getMessage());
    }
}
                                

}