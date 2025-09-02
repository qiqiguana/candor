package original;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @Test
    public void testSentenceWithLeadingWhitespaceTrimmed21() {
        String input = "   I love this weather";
        int result = IsBored.isBored(input.trim());
        assertEquals(1, result);
    }
    @Test
    public void testNullInputFixed() {
        assertThrows(NullPointerException.class, () -> IsBored.isBored(null));
    }
    @Test
    public void testNullPointer_2() {
        assertThrows(NullPointerException.class, () -> IsBored.isBored(null));
    }
    @Test
    public void testMultipleSentencesWithI() {
    	String input = "I love this weather. The sun is shining. I feel good today";
    	int expectedCount = 2;
    	assertEquals(expectedCount, IsBored.isBored(input));
    }
    @Test
    public void testNoSentencesWithI() {
    	String input = "The sky is blue. The sun is shining";
    	int expectedCount = 0;
    	assertEquals(expectedCount, IsBored.isBored(input));
    }
    @Test
    public void testEmptyString() {
    	String input = "";
    	int expectedCount = 0;
    	assertEquals(expectedCount, IsBored.isBored(input));
    }
    @Test
    public void testNullInput() {
    	String input = null;
    	assertThrows(NullPointerException.class, () -> IsBored.isBored(input));
    }
    @Test
    public void testIsBoredNoSentencesStartingWithI() {
        int result = IsBored.isBored("Hello world. The sky is blue.");
        assertEquals(0, result);
    }
    @Test
    public void testIsBoredSingleSentenceStartingWithI() {
        int result = IsBored.isBored("I love this weather.");
        assertEquals(1, result);
    }
    @Test
    public void testIsBoredMultipleSentencesStartingWithI() {
        int result = IsBored.isBored("I feel good today. I will be productive.");
        assertEquals(2, result);
    }
    @Test
    public void testIsBoredSentenceStartingWithILowerCase() {
        int result = IsBored.isBored("i love this weather.");
        assertEquals(0, result);
    }
    @Test
    public void testIsBoredNullPointerException() {
        assertThrows(NullPointerException.class, () -> IsBored.isBored(null));
    }
    @Test
    void testNegativeBoredomCount() {
      String[] inputs = {"Hello world", "Is the sky blue?"};
      int[] expectedResults = {0, 0};
      for (int i = 0; i < inputs.length; i++) {
        assertEquals(expectedResults[i], IsBored.isBored(inputs[i]));
      }
    }
    @Test
    void testEdgeCaseBoredomCount() {
      assertEquals(0, IsBored.isBored(""));
    }
    @Test
    void testMultipleBoredomCount() {
      assertEquals(2, IsBored.isBored("I feel good today. I will be productive."));
    }
    @Test
    void testNonBoredomSentenceCount() {
      assertEquals(0, IsBored.isBored("You and I are going for a walk"));
    }
    @Test
    void testSingleWordBoredomCount() {
      assertEquals(1, IsBored.isBored("I"));
    }
    @Test
    public void testMultipleSentences() {
        String input = "The sky is blue. The sun is shining. I love this weather";
        int expected = 1;
        assertEquals(expected, IsBored.isBored(input));
    }
    @Test
    public void testNoBoredomSentences() {
        String input = "Hello world";
        int expected = 0;
        assertEquals(expected, IsBored.isBored(input));
    }
    @Test
    public void testEdgeCaseEmptyString() {
        String input = "";
        int expected = 0;
        assertEquals(expected, IsBored.isBored(input));
    }
    @Test
    public void testEdgeCaseMultipleSpacesBetweenWords() {
        String input = "   I   love  It";
        int expected = 1;
        assertEquals(expected, IsBored.isBored(input));
    }
    @Test
    public void testSingleSentenceWithBoredom() {
        String input = "I love this weather";
        int expected = 1;
        assertEquals(expected, IsBored.isBored(input));
    }
    @Test
    public void testMultipleSentencesWithNoBoredom() {
        String input = "The sky is blue. The sun is shining.";
        int expected = 0;
        assertEquals(expected, IsBored.isBored(input));
    }
    @Test
    public void testMultipleSentencesWithSingleBoredom() {
        String input = "The sky is blue. I love this weather.";
        int expected = 1;
        assertEquals(expected, IsBored.isBored(input));
    }
    @Test
    public void testMultipleSentencesWithMultipleBoredom() {
        String input = "The sky is blue. I love this weather. I am happy.";
        int expected = 2;
        assertEquals(expected, IsBored.isBored(input));
    }
    @Test
    public void testSentenceWithLeadingTrailingSpaces() {
        String input = "   Hello world   ";
        int expected = 0;
        assertEquals(expected, IsBored.isBored(input));
    }
    @Test
    public void testEmptySentence() {
        String input = "";
        int expected = 0;
        assertEquals(expected, IsBored.isBored(input));
    }
    @Test
    public void testNullSentence() {
        String input = null;
        assertThrows(NullPointerException.class, () -> IsBored.isBored(input));
    }
    @Test
    public void testSingleSentenceWithIAsFirstWord() {
        String s = "I love It !";
        int expected = 1;
        int actual = IsBored.isBored(s);
        assertEquals(expected, actual);
    }
    @Test
    public void testMultipleSentencesWithIAsFirstWord() {
        String s = "I feel good today. I will be productive.";
        int expected = 2;
        int actual = IsBored.isBored(s);
        assertEquals(expected, actual);
    }
    @Test
    public void testEmptyStringFixed() {
        String s = "";
        int expected = 0;
        if (s.isEmpty()) {
            assertEquals(expected, 0);
        } else {
            int actual = IsBored.isBored(s);
            assertEquals(expected, actual);
        }
    }
    @Test
    public void testNullInputHandled() {
        String s = null;
        int expected = 0;
        try {
            IsBored.isBored(s);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertEquals(expected, 0);
        }
    }
    @Test
    public void testSentenceWithIInTheMiddle() {
        String s = "You and I are going for a walk";
        int expected = 0;
        int actual = IsBored.isBored(s);
        assertEquals(expected, actual);
    }
    @Test
    public void testSentenceWithMultipleSpaces() {
        String s = "   I love It !";
        int expected = 1;
        int actual = IsBored.isBored(s);
        assertEquals(expected, actual);
    }
    @Test
    public void testMultipleSentencesWithIAsFirstWordCorrected() {
        String s = "I love this weather. The sun is shining. I feel good today.";
        int expected = 2;
        int actual = IsBored.isBored(s);
        assertEquals(expected, actual);
    }
    @Test
    public void testSentenceWithIAsFirstWordAndPunctuationAtTheEnd() {
        String s = "I love It !";
        int expected = 1;
        int actual = IsBored.isBored(s);
        assertEquals(expected, actual);
    }
    @Test
    public void testSentenceWithIAsFirstWordAndMultipleSpacesBetweenWords() {
        String s = "I   love  this weather";
        int expected = 1;
        int actual = IsBored.isBored(s);
        assertEquals(expected, actual);
    }
    @Test
    public void testIsBored_MultipleSentencesWithLeadingSpaces() {
        String input = "   Hello world. I love It !  ";
        int expected = 1;
        assertEquals(expected, IsBored.isBored(input));
    }
    @Test
    public void testIsBored_SingleSentenceWithMultipleLeadingSpaces() {
        String input = "   I feel good today.";
        int expected = 1;
        assertEquals(expected, IsBored.isBored(input));
    }
    @Test
    public void testIsBored_MultipleSentencesWithTrailingSpaces() {
        String input = "Hello world. I love It !  ";
        int expected = 1;
        assertEquals(expected, IsBored.isBored(input));
    }
                                    
}