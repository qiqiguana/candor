package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FindClosestElements.
*/
class FindClosestElementsTest {
    @Test
    void testFindClosestElements() {
        List<Double> numbers = new ArrayList<>();
        numbers.add(1.0);
        numbers.add(2.0);
        numbers.add(3.9);
        numbers.add(4.0);
        numbers.add(5.0);
        numbers.add(2.2);
        List<Double> expected = new ArrayList<>();
        expected.add(3.9);
        expected.add(4.0);
        assertEquals(expected, FindClosestElements.findClosestElements(numbers));
    }
    
    @Test
        public void testNothing(){
            FindClosestElements s = new FindClosestElements();
            }
    @Test
    public void testFindClosestElementsWithDuplicateNumbersFixed() {
        List<Double> numbers = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 2.0);
        List<Double> expected = Arrays.asList(2.0, 2.0);
        assertEquals(expected, FindClosestElements.findClosestElements(numbers));
    }
                                    
}