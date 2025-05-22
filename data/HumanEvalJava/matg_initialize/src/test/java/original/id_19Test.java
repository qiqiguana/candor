package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortNumbers.
*/
class SortNumbersTest {
    @Test
    void testSortNumbers_SortedString_ReturnsSortedString() {
        String numbers = "five zero four seven nine eight";
        String expected = "zero four five seven eight nine";
        assertEquals(expected, SortNumbers.sortNumbers(numbers));
    }
}