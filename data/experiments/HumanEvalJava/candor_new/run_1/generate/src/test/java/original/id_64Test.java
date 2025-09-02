package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of VowelsCount.
*/
class VowelsCountTest {
    @Test
    void testVowelsCount_YAtEnd() {
        String word = "bye";
        int expected = 1;
        int actual = VowelsCount.vowelsCount(word);
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            VowelsCount s = new VowelsCount();
            }
    @Test
    void testVowelsCountEmptyString() {
        assertEquals(0, VowelsCount.vowelsCount(""));
    }
    @Test
    void testVowelyAtEndOfString() {
        int result = VowelsCount.vowelsCount("key");
        assertEquals(2, result);
    }
    @Test
    void testUppercaseVowelyAtEndOfString() {
        int result = VowelsCount.vowelsCount("keY");
        assertEquals(2, result);
    }
    @Test
    void testMultipleVowelsInString() {
        int result = VowelsCount.vowelsCount("abcde");
        assertEquals(2, result);
    }
    @Test
    public void testVowelsCountWithLowercaseVowels() {
        String[] input = {"hello", "world"};
        int expected = 3;
        assertEquals(expected, VowelsCount.vowelsCount(input[0]) + VowelsCount.vowelsCount(input[1]));
    }
    @Test
    public void testVowelsCountWithUppercaseVowels() {
        String[] input = {"HELLO", "WORLD"};
        int expected = 3;
        assertEquals(expected, VowelsCount.vowelsCount(input[0]) + VowelsCount.vowelsCount(input[1]));
    }
    @Test
    public void VowelsCountTest_multipleVowels() {
        String input = new String("aeiouAEIOU");
        int expected_result = 10;
        assertEquals(expected_result, VowelsCount.vowelsCount(input));
    }
                                    
}