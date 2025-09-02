package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetClosestVowel.
*/
class GetClosestVowelTest {
    @Test
    void testGetClosestVowel() {
        String word = "yogurt";
        String result = GetClosestVowel.getClosestVowel(word);
        assertEquals("u", result);
    }
    
    @Test
        public void testNothing(){
            GetClosestVowel s = new GetClosestVowel();
            }
    @Test
    public void testEmptyStringInput() {
        String result = GetClosestVowel.getClosestVowel("");
        assertEquals("", result);
    }
    @Test
    public void getClosestVowel_NoConsonants_ReturnsEmptyString() {
        String result = GetClosestVowel.getClosestVowel("aeiou");
        assertEquals("", result);
    }
    @Test
    public void testClosestVowelWithNoVowelsBetweenConsonants() {
        String word = "quick";
        String expected_result = "";
        assertEquals(expected_result, GetClosestVowel.getClosestVowel(word));
    }
                                    
}