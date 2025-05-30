package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Specialfilter.
*/
class SpecialfilterTest {

    @Test
    void test_specialfilter_with_numbers_starting_and_ending_with_odd_digits() {
        List<Object> numbers = new ArrayList<>(Arrays.asList(13, 15, 17, 19, 31));
        assertEquals(0, Specialfilter.specialfilter(numbers));
    }
}