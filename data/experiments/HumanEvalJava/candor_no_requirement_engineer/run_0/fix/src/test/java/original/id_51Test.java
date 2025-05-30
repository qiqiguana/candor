package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RemoveVowels.
*/
class RemoveVowelsTest {
    @Test
    void testRemoveVowels_singleCharacterNoVowel() {
        String text = "b";
        String expected = "b";
        String actual = RemoveVowels.removeVowels(text);
        assertEquals(expected, actual);
    }
    
    @Test
        void testNothing(){
            RemoveVowels s = new RemoveVowels();
            }
    @Test
    public void testRemoveVowels() {
        String text = "HelloWorld";
        assertEquals("HllWrld", RemoveVowels.removeVowels(text));
    }
    @Test
    public void testRemoveVowelsEmptyString() {
        String text = "";
        assertEquals("", RemoveVowels.removeVowels(text));
    }
    @Test
    public void testRemoveVowelsAllVowels() {
        String text = "aeiouAEIOU";
        assertEquals("", RemoveVowels.removeVowels(text));
    }
    @Test
    public void testRemoveVowelsNoVowels() {
        String text = "bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ";
        assertEquals("bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ", RemoveVowels.removeVowels(text));
    }
    @Test
    public void testRemoveVowelsSingleCharacter() {
        String text = "a";
        assertEquals("", RemoveVowels.removeVowels(text));
    }
    @Test
    public void testRemoveVowelsMultiLine() {
        String text = "Hello\nWorld";
        assertEquals("Hll\nWrld", RemoveVowels.removeVowels(text));
    }
                                    
}