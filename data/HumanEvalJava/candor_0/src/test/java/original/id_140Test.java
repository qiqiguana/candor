package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FixSpaces.
*/
class FixSpacesTest {
    @Test
    void test_fixSpaces_replacesConsecutiveSpacesWithHyphen() {
        String result = FixSpaces.fixSpaces("Exa   mple");
        assertEquals("Exa-mple", result);
    }
    
    @Test
        public void testNothing(){
            FixSpaces s = new FixSpaces();
            }
    @Test
    public void testFixSpacesMultipleConsecutiveSpaces() {
        String input = "Example   1";
        String expectedOutput = "Example-1";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesLeadingAndTrailingSpaces() {
        String input = "   Example   ";
        String expectedOutput = "-Example-";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesSingleSpace() {
        String input = "Example 1";
        String expectedOutput = "Example_1";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesNoSpaces() {
        String input = "Example";
        String expectedOutput = "Example";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesMultipleConsecutiveUnderscores() {
        String input = "Example__1";
        String expectedOutput = "Example__1";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesSingleWordNoSpace() {
    	String input = "Example";
    	String expectedOutput = "Example";
    	assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesMultipleWordsSingleSpace() {
    	String input = "Example 1";
    	String expectedOutput = "Example_1";
    	assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesMultipleWordsMultipleSpaces() {
    	String input = "Yellow Yellow  Dirty  Fellow";
    	String expectedOutput = "Yellow_Yellow__Dirty__Fellow";
    	assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesSingleWordMultipleLeadingTrailingSpaces() {
    	String input = "   Exa 1 2 2 mple   ";
    	String expectedOutput = "-Exa_1_2_2_mple-";
    	assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesEmptyInput() {
    	String input = "";
    	assertEquals("", FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesMultipleWordsLeadingTrailingSpacesFixed() {
    	String input = "   Yellow Yellow  Dirty  Fellow   ";
    	String expectedOutput = "-Yellow_Yellow__Dirty__Fellow-";
    	assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesOnlySpacesCorrected() {
        String input = "   ";
        assertEquals("-", FixSpaces.fixSpaces(input));
    }
    @Test
    public void testSingleSpaceReplacement() {
    	String input = "Example 1";
    	String expectedOutput = "Example_1";
    	assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testMultipleSpacesReplacement() {
    	String input = " Example   3";
    	String expectedOutput = "_Example-3";
    	assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testLeadingAndTrailingSpaces() {
    	String input = "   Exa 1 2 2 mple   ";
    	String expectedOutput = "-Exa_1_2_2_mple-";
    	assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testNoSpaces() {
    	String input = "Example";
    	String expectedOutput = "Example";
    	assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testConsecutiveSpacesAtBeginning() {
    	String input = "   Example";
    	String expectedOutput = "-Example";
    	assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesOnlySpacesFixed() {
        String input = "   ";
        assertEquals("-", FixSpaces.fixSpaces(input));
    }
    @Test
    public void testConsecutiveSpacesAtEnd() {
    	String input = "Example   ";
    	String expectedOutput = "Example-";
    	assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpaces_NormalConditions() {
    String[] input = {"Example", "Example 1", " Example 2", " Example   3"};
    String[] expectedResults = {"Example", "Example_1", "_Example_2", "_Example-3"};
    for (int i = 0; i < input.length; i++) {
    String result = FixSpaces.fixSpaces(input[i]);
    assertEquals(expectedResults[i], result);
    }
    }
    @Test
    public void testFixSpaces_EmptyString() {
    String input = "";
    String expectedResult = "";
    String result = FixSpaces.fixSpaces(input);
    assertEquals(expectedResult, result);
    }
    @Test
    public void testFixSpaces_SingleSpace() {
    String input = " ";
    String expectedResult = "_";
    String result = FixSpaces.fixSpaces(input);
    assertEquals(expectedResult, result);
    }
    @Test
    public void testFixSpaces_MultipleConsecutiveSpaces() {
    String input = "   Exa 1 2 2 mple";
    String expectedResult = "-Exa_1_2_2_mple";
    String result = FixSpaces.fixSpaces(input);
    assertEquals(expectedResult, result);
    }
    @Test
    public void testFixSpaces_MultipleWords() {
    String input = "Hello World";
    String expectedResult = "Hello_World";
    String result = FixSpaces.fixSpaces(input);
    assertEquals(expectedResult, result);
    }
    @Test
    public void testFixSpaces_Trimmed() {
    String input = "   Hello World   ";
    String expectedResult = "-Hello_World-";
    String result = FixSpaces.fixSpaces(input);
    assertEquals(expectedResult, result);
    }
                                    
}