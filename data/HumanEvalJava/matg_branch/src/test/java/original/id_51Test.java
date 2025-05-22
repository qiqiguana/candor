package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RemoveVowels.
*/
class RemoveVowelsTest {
    @Test
    void removeVowels_RemovesAllVowels() {
        String result = RemoveVowels.removeVowels("abcdef");
        assertEquals("bcdf", result);
    }
    
    @Test
        public void testNothing(){
            RemoveVowels s = new RemoveVowels();
            }
    @Test
    public void testRemoveVowelsHappyPath() {
        String input = "bcdf";
        String expectedResult = "bcdf";
        assertEquals(expectedResult, RemoveVowels.removeVowels(input));
    }
    @Test
    public void testRemoveVowelsSadPath() {
        String input = "aaaaa";
        String expectedResult = "";
        assertEquals(expectedResult, RemoveVowels.removeVowels(input));
    }
    @Test
    public void testRemoveVowelsEdgeCaseEmptyString() {
        String input = "";
        String expectedResult = "";
        assertEquals(expectedResult, RemoveVowels.removeVowels(input));
    }
    @Test
    public void testRemoveVowelsEdgeCaseSingleCharacter() {
        String input = "b";
        String expectedResult = "b";
        assertEquals(expectedResult, RemoveVowels.removeVowels(input));
    }
    @Test
    public void testRemoveVowelsMultiLineString() {
        String input = "abcdef\nghijklm";
        String expectedResult = "bcdf\nghjklm";
        assertEquals(expectedResult, RemoveVowels.removeVowels(input));
    }
    @Test
    public void testRemoveVowelsNonASCIICahracters_1() {
        String input = "zbcd";
        String expectedResult = "zbcd";
        assertEquals(expectedResult, RemoveVowels.removeVowels(input));
    }
                                    
}