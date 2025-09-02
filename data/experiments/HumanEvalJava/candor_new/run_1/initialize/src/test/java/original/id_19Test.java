package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortNumbers.
*/
class SortNumbersTest {
    @Test
    void testSortNumbers_SimpleInput_ReturnsSortedString() {
        String input = "three one five";
        String expectedOutput = "one three five";
        assertEquals(expectedOutput, SortNumbers.sortNumbers(input));
    }
}