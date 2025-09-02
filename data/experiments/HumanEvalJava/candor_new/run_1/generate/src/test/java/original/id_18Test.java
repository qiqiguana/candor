package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HowManyTimes.
*/
class HowManyTimesTest {
    @Test
    void testHowManyTimes_WithEmptyString_ReturnsZero() {
        // Arrange and Act
        int result = HowManyTimes.howManyTimes("", "a");
        // Assert
        assertEquals(0, result);
    }
    
    @Test
        public void testNothing(){
            HowManyTimes s = new HowManyTimes();
            }
    @Test
    void test_howManyTimes_with_non_empty_string_and_empty_substring_2() {
        String string = "hello";
        String substring = "";
        int expected = string.length();
        int result = HowManyTimes.howManyTimes(string, substring);
        assertEquals(expected, result);
    }
    @Test
    void test_howManyTimes_with_overlapping_matches() {
        String string = "aaaa";
        String substring = "aa";
        int expected = 3;
        int result = HowManyTimes.howManyTimes(string, substring);
        assertEquals(expected, result);
    }
                                    
}