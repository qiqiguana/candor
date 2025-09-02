package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HowManyTimes.
*/
class HowManyTimesTest {
    @Test
    void testHowManyTimes_WhenInputStringIsEmpty_ReturnsZero() {
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
    public void testEmptyString() {
        int result = HowManyTimes.howManyTimes("", "a");
        assertEquals(0, result);
    }
    @Test
    public void testSingleCharacterSubstring() {
        int result = HowManyTimes.howManyTimes("aaa", "a");
        assertEquals(3, result);
    }
    @Test
    public void testMultiCharacterSubstring() {
        int result = HowManyTimes.howManyTimes("aaaa", "aa");
        assertEquals(3, result);
    }
                                    
}