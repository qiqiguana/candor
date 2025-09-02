package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FixSpaces.
*/
class FixSpacesTest {
    @Test
    void fixSpaces_replacesConsecutiveSpacesWithHyphen1() {
        // Given
        String input = "Example   mple";
        // When
        String result = FixSpaces.fixSpaces(input);
        // Then
        assertEquals("Example-mple", result);
    }
    
    @Test
        public void testNothing(){
            FixSpaces s = new FixSpaces();
            }
    @Test
    public void TestFixSpacesWithSingleSpace() {
    	String input = "Hello World";
    	String expectedOutput = "Hello_World";
    	assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
    @Test
    public void testFixSpacesWithMoreThanTwoConsecutiveSpacesAtTheEnd() {
        String result = FixSpaces.fixSpaces("   ");
        assertEquals("-", result);
    }
    @Test
    public void testFixSpacesWithSingleSpaceAtTheEnd() {
        String result = FixSpaces.fixSpaces(" ");
        assertEquals("_", result);
    }
                                    
}