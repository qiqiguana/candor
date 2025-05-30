package oracle1;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HasCloseElements.
*/
class HasCloseElementsTest {
    @Test
    void testHasCloseElements_ThresholdMet_ReturnsTrue() {
        List<Double> numbers = new ArrayList<>();
        numbers.add(1.0);
        numbers.add(2.8);
        numbers.add(3.0);
        Double threshold = 0.3;
        assertTrue(HasCloseElements.hasCloseElements(numbers, threshold));
    }
    
    @Test
        void testNothing(){
            HasCloseElements s = new HasCloseElements();
            }
    @Test
    public void TestHasCloseElements_EmptyList_False() {
        List<Double> numbers = new ArrayList<>();
        Double threshold = 0.5;
        assertFalse(HasCloseElements.hasCloseElements(numbers, threshold));
    }
    @Test
    public void TestHasCloseElements_DuplicateNumbers_ReturnsTrue_2() {
        List<Double> numbers = java.util.Arrays.asList(1.0, 2.0, 2.0);
        Double threshold = 0.5;
        assertTrue(HasCloseElements.hasCloseElements(numbers, threshold));
    }
                                    
}