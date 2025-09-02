package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RemoveVowels.
*/
class RemoveVowelsTest {
    @Test
    void testRemoveVowelsFromSingleWord() {
        String input = "abcdef";
        String expectedOutput = "bcdf";
        assertEquals(expectedOutput, RemoveVowels.removeVowels(input));
    }
    
    @Test
        public void testNothing(){
            RemoveVowels s = new RemoveVowels();
            }
    @Test
    public void testRemoveVowelsClassInstantiation() {
        new RemoveVowels();
    }
    @Test
    public void testRemoveVowelsMethodWithVowelsAndConsonants() {
        String text = "Hello World";
        String expected = "Hll Wrld";
        assertEquals(expected, RemoveVowels.removeVowels(text));
    }
    @Test
    public void testRemoveVowelsMethodWithOnlyVowels() {
        String text = "aeiouAEIOU";
        String expected = "";
        assertEquals(expected, RemoveVowels.removeVowels(text));
    }
                                    
}