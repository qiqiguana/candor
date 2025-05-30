package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HowManyTimes.
*/
class HowManyTimesTest {
    @Test
    void test_howManyTimes_WithOverlap_ReturnsCount() {
        String string = "aaaa";
        String substring = "aa";
        int expectedCount = 3;
        int actualCount = HowManyTimes.howManyTimes(string, substring);
        assertEquals(expectedCount, actualCount);
    }
    
    @Test
        void testNothing(){
            HowManyTimes s = new HowManyTimes();
            }
    @Test
    public void testEmptyStringAndSubstring() {
        assertEquals(0, HowManyTimes.howManyTimes("", "a"));
    }
    @Test
    public void testEmptyStringAndNonExistentSubstring() {
        assertEquals(0, HowManyTimes.howManyTimes("", "x"));
    }
    @Test
    public void testSingleCharacterStringAndSameCharacterSubstring() {
        assertEquals(1, HowManyTimes.howManyTimes("a", "a"));
    }
    @Test
    public void testMultipleCharacterStringAndSingleCharacterSubstring() {
        assertEquals(3, HowManyTimes.howManyTimes("aaa", "a"));
    }
    @Test
    public void testMultipleCharacterStringAndMultiCharacterSubstring() {
        assertEquals(3, HowManyTimes.howManyTimes("aaaa", "aa"));
    }
    @Test
    public void testNonMatchingSubstring() {
        assertEquals(0, HowManyTimes.howManyTimes("abc", "def"));
    }
    @Test
    public void testNullString() {
        assertThrows(NullPointerException.class, () -> HowManyTimes.howManyTimes(null, "a"));
    }
                                    
}