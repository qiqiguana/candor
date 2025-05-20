package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
* Test class of HasCloseElements.
*/
class HasCloseElementsTest {
    @Test
    void testHasCloseElementsThresholdMet() {
        List<Double> numbers = new ArrayList<>();
        numbers.add(1.0);
        numbers.add(2.8);
        numbers.add(3.0);
        numbers.add(4.0);
        numbers.add(5.0);
        numbers.add(2.0);
        assertTrue(HasCloseElements.hasCloseElements(numbers, 0.3));
    }
}