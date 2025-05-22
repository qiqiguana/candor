package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of VowelsCount.
*/
class VowelsCountTest {
    @Test
    void testVowelsCount_withStringContainingOnlyVowels_ReturnsCorrectVowelCount() {
        String s = "aeiouAEIOU";
        int expected = 10;
        int actual = VowelsCount.vowelsCount(s);
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            VowelsCount s = new VowelsCount();
            }
    @Test
    public void TestVowelsAtBeginning() {
        String s = "aeiou";
        assertEquals(5, VowelsCount.vowelsCount(s));
    }
    @Test
    public void TestConsecutiveVowels() {
        String s = "aeeioo";
        assertEquals(6, VowelsCount.vowelsCount(s));
    }
    @Test
    public void TestNoVowels() {
        String s = "bcdfghjklmnpqrstvwxyz";
        assertEquals(0, VowelsCount.vowelsCount(s));
    }
    @Test
    public void TestSingleCharacterVowel() {
        String s = "a";
        assertEquals(1, VowelsCount.vowelsCount(s));
    }
    @Test
    public void TestEmptyString() {
        String s = "";
        assertEquals(0, VowelsCount.vowelsCount(s));
    }
    @Test
    public void TestYAsVowelAtEndOfWord() {
        String s = "my";
        assertEquals(1, VowelsCount.vowelsCount(s));
    }
    @Test
    public void TestNullString() {
        String s = null;
        assertThrows(NullPointerException.class, () -> VowelsCount.vowelsCount(s));
    }
                                    
}