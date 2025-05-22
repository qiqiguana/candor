package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsBored.
*/
class IsBoredTest {
    @Test
    void testIsBoredWithMultipleSentences() {
        String input = "I love It ! The sky is blue. The sun is shining.";
        int expected = 1;
        assertEquals(expected, IsBored.isBored(input));
    }
    
    @Test
        public void testNothing(){
            IsBored s = new IsBored();
            }
    @Test
    public void test_IsBored_SingleSentenceWithI() {
        String input = "I love this weather";
        int expected = 1;
        assertEquals(expected, IsBored.isBored(input));
    }
    @Test
    public void test_IsBored_MultipleSentencesWithMultipleIs() {
        String input = "I feel good today. I will be productive. will kill It";
        int expected = 2;
        assertEquals(expected, IsBored.isBored(input));
    }
    @Test
    public void test_IsBored_NoSentencesStartWithI() {
        String input = "Hello world";
        int expected = 0;
        assertEquals(expected, IsBored.isBored(input));
    }
    @Test
    public void test_IsBored_EmptyString() {
        String input = "";
        int expected = 0;
        assertEquals(expected, IsBored.isBored(input));
    }
    @Test
    public void test_IsBored_SingleWordThatIsNotI() {
        String input = "bIt";
        int expected = 0;
        assertEquals(expected, IsBored.isBored(input));
    }
    @Test
    public void test_IsBored_IgnoreCaseAfterFirstWord() {
        String input = "I love THIS weather";
        int expected = 1;
        assertEquals(expected, IsBored.isBored(input));
    }
                                    
}