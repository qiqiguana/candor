package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CountUpper.
*/
class CountUpperTest {
    @Test
    void test_count_upper_even_indices() {
        String s = "aBCdEf";
        int expected = 1;
        assertEquals(expected, CountUpper.countUpper(s));
    }
}
