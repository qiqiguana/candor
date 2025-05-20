package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortNumbers.
*/
class SortNumbersTest {
    @Test
    void testSortNumbers_SimpleCase() {
        String numbers = "three one five";
        String expected = "one three five";
        assertEquals(expected, SortNumbers.sortNumbers(numbers));
    }
}