package original;

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
    void testFindClosestElements_ReturnsCorrectResult_WhenInputListHasAtLeastTwoElements1() {
        List<Double> numbers = new ArrayList<>();
        numbers.add(1.0);
        numbers.add(2.0);
        numbers.add(3.9);
        numbers.add(4.0);
        numbers.add(5.0);
        numbers.add(2.0);
        List<Double> expected = new ArrayList<>();
        expected.add(2.0);
        expected.add(2.0);
        assertEquals(expected, FindClosestElements.findClosestElements(numbers));
    }
    
    @Test
        public void testNothing(){
            FindClosestElements s = new FindClosestElements();
            }
                                    
}