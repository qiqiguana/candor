package original;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HasCloseElements.
*/
class HasCloseElementsTest {
    @Test
    void testHasCloseElements_DifferentNumbers_ThresholdLessThanDifference_ReturnsFalse() {
        List<Double> numbers = new ArrayList<>();
        numbers.add(1.0);
        numbers.add(2.8);
        numbers.add(3.9);
        Double threshold = 0.05;
        assertFalse(HasCloseElements.hasCloseElements(numbers, threshold));
    }
}