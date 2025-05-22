package original;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Specialfilter.
*/
class SpecialfilterTest {

    @Test
    void testSpecialFilter_OddFirstAndLastDigits_ReturnsExpectedResult() {
        List<Object> numbers = Arrays.asList(15, -73, 14, -15);
        int expectedResult = 1;
        int actualResult = Specialfilter.specialfilter(numbers);
        assertEquals(expectedResult, actualResult);
    }

}
