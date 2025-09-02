package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortNumbers.
*/
class SortNumbersTest {
    @Test
    void testSortNumbers() {
        String numbers = "five zero four seven nine eight";
        String expectedSortedNumbers = "zero four five seven eight nine";
        assertEquals(expectedSortedNumbers, SortNumbers.sortNumbers(numbers));
    }
}