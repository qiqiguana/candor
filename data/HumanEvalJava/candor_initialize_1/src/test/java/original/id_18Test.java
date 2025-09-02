package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HowManyTimes.
*/
class HowManyTimesTest {
    @Test
    void test_howManyTimes_WhenSubstringIsContainedInString_ReturnsNumberOfOccurrences() {
        String string = "aaa";
        String substring = "a";
        int expectedCount = 3;
        int actualCount = HowManyTimes.howManyTimes(string, substring);
        assertEquals(expectedCount, actualCount);
    }
    
    @Test
        public void testNothing(){
            HowManyTimes s = new HowManyTimes();
            }
    @Test
    public void testEmptyStringAndSubstring() {
        assertEquals(0, HowManyTimes.howManyTimes("", ""));
    }
    @Test
    public void testEmptyStringAndNonEmptySubstring() {
        assertEquals(0, HowManyTimes.howManyTimes("", "world"));
    }
    @Test
    public void testSameStringAndSubstring() {
        assertEquals(3, HowManyTimes.howManyTimes("aaa", "a"));
    }
    @Test
    public void testOverlappingSubstring() {
        assertEquals(3, HowManyTimes.howManyTimes("aaaa", "aa"));
    }
                                    
}