package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetClosestVowel.
*/
class GetClosestVowelTest {
    @Test
    void testGetClosestVowel() {
        assertEquals("u", GetClosestVowel.getClosestVowel("yogurt"));
    }
    
    @Test
        public void testNothing(){
            GetClosestVowel s = new GetClosestVowel();
            }
    @Test
    public void testGetClosestVowelClassInitialization() {
    	GetClosestVowel closestVowel = new GetClosestVowel();
    	assertNotNull(closestVowel);
    }
    @Test
    public void testGetClosestVowelNoVowelsInBetween() {
    	String word = "bc";
    	String result = GetClosestVowel.getClosestVowel(word);
    	assertEquals("", result);
    }
    @Test
    public void testGetClosestVowelVowelAtBeginning() {
    	String word = "abc";
    	String result = GetClosestVowel.getClosestVowel(word);
    	assertEquals("", result);
    }
    @Test
    public void testGetClosestVowelVowelAtEnd() {
    	String word = "cba";
    	String result = GetClosestVowel.getClosestVowel(word);
    	assertEquals("", result);
    }
    @Test
    public void TestVowelBetweenTwoConsonants() {
        String word = "yogurt";
        String expected = "u";
        String result = GetClosestVowel.getClosestVowel(word);
        assertEquals(expected, result);
    }
    @Test
    public void TestNoVowelBetweenConsonants() {
        String word = "quick";
        String expected = "";
        String result = GetClosestVowel.getClosestVowel(word);
        assertEquals(expected, result);
    }
                                    
}