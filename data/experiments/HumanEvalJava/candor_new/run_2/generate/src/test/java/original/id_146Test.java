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
    void test_specialfilter_with_odd_digits_at_both_ends_and_greater_than_ten() {
        List<Object> nums = Arrays.asList(15, -73, 14, -15);
        assertEquals(1, Specialfilter.specialfilter(nums));
    }
    
    @Test
        public void testNothing(){
            Specialfilter s = new Specialfilter();
            }
    @Test
    public void test_specialfilter_with_two_digit_number_having_even_last_digit() {
        List<Object> input = Arrays.asList(12, 24, 36);
        int expected_result = 0;
        assertEquals(expected_result, Specialfilter.specialfilter(input));
    }
                                    
}