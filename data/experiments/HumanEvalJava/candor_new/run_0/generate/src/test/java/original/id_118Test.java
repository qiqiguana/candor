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
    public void TestGetClosestVowelWithNoVowelInBetweenConsonants() {
    	// Given
    	String word = "quick";
    	// When
    	String result = GetClosestVowel.getClosestVowel(word);
    	// Then
    	assertEquals("", result);
    }
                                    
}