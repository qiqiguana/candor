package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FixSpaces.
*/
class FixSpacesTest {
    @Test
    void testFixSpaces_ReplacesMultipleConsecutiveSpacesWithHyphen() {
        String input = "Exa   mple";
        String expectedOutput = "Exa-mple";
        String actualOutput = FixSpaces.fixSpaces(input);
        assertEquals(expectedOutput, actualOutput);
    }
    
    @Test
        public void testNothing(){
            FixSpaces s = new FixSpaces();
            }
    @Test
    public void testFixSpacesWithNoSpaces() {
        String input = "Example";
        String expectedOutput = "Example";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesWithSingleSpace() {
        String input = "Example 1";
        String expectedOutput = "Example_1";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesWithMultipleSpaces() {
        String input = " Example   3";
        String expectedOutput = "_Example-3";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesWithOnlySpacesFixed() {
        String input = "   ";
        String expectedOutput = "-";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesWithNullInput() {
        String input = null;
        assertThrows(NullPointerException.class, () -> FixSpaces.fixSpaces(input));
    }
    @Test
    public void testSingleSpaceReplacement() {
        String input = "Hello World";
        String expectedOutput = "Hello_World";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testMultipleConsecutiveSpacesReplacement() {
        String input = "Hello   World";
        String expectedOutput = "Hello-World";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testNoSpaces() {
        String input = "HelloWorld";
        String expectedOutput = "HelloWorld";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testSingleCharacterInput() {
        String input = "a";
        String expectedOutput = "a";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testEmptyStringInput() {
        String input = "";
        String expectedOutput = "";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesHappyPath() {
    	String input = "Example";
    	String expectedOutput = "Example";
    	assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesSingleSpace() {
    	String input = "Example 1";
    	String expectedOutput = "Example_1";
    	assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesMultipleSpaces() {
    	String input = " Example   3";
    	String expectedOutput = "_Example-3";
    	assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesNoSpaces() {
    	String input = "MudasirHanif";
    	String expectedOutput = "MudasirHanif";
    	assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesSingleSpaceAtEndFixed() {
        String input = "MudasirHanif ";
        String expectedOutput = "MudasirHanif_";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
                                    
}