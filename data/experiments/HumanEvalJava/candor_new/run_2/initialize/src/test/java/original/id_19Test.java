package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortNumbers.
*/
class SortNumbersTest {
    @Test
    void sortNumbers_SortedInput_ReturnsSame() {
        String input = "one two three";
        String expectedOutput = "one two three";
        assertEquals(expectedOutput, SortNumbers.sortNumbers(input));
    }
}