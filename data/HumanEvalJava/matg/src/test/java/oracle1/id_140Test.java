package oracle1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FixSpaces.
*/
class FixSpacesTest {

    @Test
    void testFixSpacesConsecutiveSpaces() {
        String input = "Exa   mple";
        String expectedOutput = "Exa-mple";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    
    @Test
     void testNothing(){
         FixSpaces s = new FixSpaces();
         }
    @Test
    public void positiveSingleWord() {
        String input = "Example";
        String expectedOutput = "Example";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void positiveTwoWordsWithSpace1() {
        String input = "Example 1";
        String expectedOutput = "Example_1";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void positiveLeadingAndTrailingSpaces2() {
        String input = " Example 2 ";
        String expectedOutput = "_Example_2_";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void negativeNullInput3() {
        assertThrows(NullPointerException.class, () -> FixSpaces.fixSpaces(null));
    }
    @Test
    public void edgeCaseConsecutiveSpaces4Fixed() {
        String input = "Example   3";
        String expectedOutput = "Example-3";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void test_fixSpaces_SingleWord() {
        String text = "Example";
        String expected = "Example";
        assertEquals(expected, FixSpaces.fixSpaces(text));
    }
    @Test
    public void test_fixSpaces_MultipleWords() {
        String text = "Example 1";
        String expected = "Example_1";
        assertEquals(expected, FixSpaces.fixSpaces(text));
    }
    @Test
    public void test_fixSpaces_LeadingAndTrailingSpaces() {
    	String text = "   Exa 1 2 2 mple   ";
    	String expected = "-Exa_1_2_2_mple-";
    	assertEquals(expected, FixSpaces.fixSpaces(text));
    }
    @Test
    public void test_fixSpaces_NullInput() {
        String text = null;
        assertThrows(NullPointerException.class, () -> FixSpaces.fixSpaces(text));
    }
    @Test
    public void test_fixSpaces_ConsecutiveSpaces() {
        String text = "Exa   mple";
        String expected = "Exa-mple";
        assertEquals(expected, FixSpaces.fixSpaces(text));
    }
    @Test
    void testFixSpacesSingleSpace() {
        String input = "Hello World";
        String expectedOutput = "Hello_World";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
                                  
}