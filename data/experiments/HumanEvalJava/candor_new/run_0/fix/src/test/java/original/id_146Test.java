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
    void testSpecialFilter() {
        List<Object> nums = Arrays.asList(15, -73, 14, -15);
        int result = Specialfilter.specialfilter(nums);
        assertEquals(1, result);
    }
    
    @Test
        public void testNothing(){
            Specialfilter s = new Specialfilter();
            }
    @Test
    public void test_specialfilter_with_multiple_numbers_meeting_the_condition() {
        List<Object> nums = Arrays.asList(33, -2, -3, 45, 21, 109);
        int expected_result = 2;
        assertEquals(expected_result, Specialfilter.specialfilter(nums));
    }
                                    
}