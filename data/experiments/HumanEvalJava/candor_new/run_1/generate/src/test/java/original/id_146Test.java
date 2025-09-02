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
    void testSpecialFilter_WhenListContainsNumbersGreaterThan10AndFirstAndLastDigitsAreOdd_ReturnsExpectedResult() {
        List<Object> nums = Arrays.asList(33, -2, -3, 45, 21, 109);
        int expectedCount = 2;
        int actualCount = Specialfilter.specialfilter(nums);
        assertEquals(expectedCount, actualCount);
    }
    
    @Test
        public void testNothing(){
            Specialfilter s = new Specialfilter();
            }
    @Test
    public void test_specialfilter_with_number_greater_than_10_and_both_first_and_last_digits_are_odd1() {
        List<Object> nums = Arrays.asList(15, -73, 14, -15);
        int expected_result = 1;
        assertEquals(expected_result, Specialfilter.specialfilter(nums));
    }
                                    
}