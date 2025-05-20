package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of VowelsCount.
*/
class VowelsCountTest {

    @Test
    void testVowelCount_YAsAVowelAtTheEnd() {
        String input = "key";
        int expected = 2;
        assertEquals(expected, VowelsCount.vowelsCount(input));
    }
    
    @Test
        void testNothing(){
            VowelsCount s = new VowelsCount();
            }
    @Test
    public void testVowelsCountWithEmptyString() {
        assertEquals(0, VowelsCount.vowelsCount(""));
    }
    @Test
    public void testVowelsCountWithSingleVowel() {
        assertEquals(1, VowelsCount.vowelsCount("a"));
    }
    @Test
    public void testVowelsCountWithMultipleVowels() {
        assertEquals(5, VowelsCount.vowelsCount("aeiou"));
    }
    @Test
    public void testVowelsCountWithYAtEnd() {
        assertEquals(1, VowelsCount.vowelsCount("by"));
    }
    @Test
    public void testVowelsCountWithMixedCase() {
        assertEquals(6, VowelsCount.vowelsCount("AeIoUy"));
    }
                                    
}