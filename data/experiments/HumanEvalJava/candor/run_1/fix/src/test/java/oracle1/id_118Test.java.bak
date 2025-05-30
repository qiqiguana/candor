package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetClosestVowel.
*/
class GetClosestVowelTest {
    @Test
    void testGetClosestVowel_yogurt() {
        String word = "yogurt";
        String expected = "u";
        String actual = GetClosestVowel.getClosestVowel(word);
        assertEquals(expected, actual);
    }
    
    @Test
        void testNothing(){
            GetClosestVowel s = new GetClosestVowel();
            }
    @Test
    public void TestGetClosestVowel_EmptyString_ReturnsEmptyString() {
        assertEquals("", GetClosestVowel.getClosestVowel(""));
    }
    @Test
    public void TestGetClosestVowel_SingleCharacter_ReturnsEmptyString() {
        assertEquals("", GetClosestVowel.getClosestVowel("a"));
    }
    @Test
    public void TestGetClosestVowel_NoVowelsBetweenConsonants_ReturnsEmptyString() {
        assertEquals("", GetClosestVowel.getClosestVowel("bc"));
    }
    @Test
    public void TestGetClosestVowel_VowelAtBeginningOrEnd_ReturnsEmptyString() {
        assertEquals("", GetClosestVowel.getClosestVowel("ab"));
    }
    @Test
    public void TestGetClosestVowel_VowelBetweenConsonants_ReturnsVowel() {
        assertEquals("o", GetClosestVowel.getClosestVowel("most"));
    }
                                    
}