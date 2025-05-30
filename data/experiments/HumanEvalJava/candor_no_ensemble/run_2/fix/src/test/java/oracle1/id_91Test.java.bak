package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsBored.
*/
class IsBoredTest {
    @Test
    void testIsBored_shouldReturnZeroForNoISentences() {
        String input = "Hello world";
        int expected = 0;
        int actual = IsBored.isBored(input);
        assertEquals(expected, actual);
    }
    
    @Test
        void testNothing(){
            IsBored s = new IsBored();
            }
    @Test
    public void testSingleSentenceWithI() {
    	String input = "I love this weather";
    	assertEquals(1, IsBored.isBored(input));
    }
    @Test
    public void testSingleSentenceWithoutI() {
    	String input = "The sky is blue.";
    	assertEquals(0, IsBored.isBored(input));
    }
    @Test
    public void testMultipleSentencesWithI() {
    	String input = "I love this weather. The sun is shining. I love it.";
    	assertEquals(2, IsBored.isBored(input));
    }
    @Test
    public void testMultipleSentencesWithoutI() {
    	String input = "The sky is blue. The sun is shining.";
    	assertEquals(0, IsBored.isBored(input));
    }
    @Test
    public void testSentenceWithLeadingSpaces() {
    	String input = "   I love this weather";
    	assertEquals(1, IsBored.isBored(input));
    }
    @Test
    public void testEmptyInput() {
    	String input = "";
    	assertEquals(0, IsBored.isBored(input));
    }
    @Test
    public void testNullInput() {
    	String input = null;
    	assertThrows(NullPointerException.class, () -> IsBored.isBored(input));
    }
                                    
}