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
                                    
}