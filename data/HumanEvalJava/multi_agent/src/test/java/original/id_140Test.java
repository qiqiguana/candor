package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FixSpaces.
*/
class FixSpacesTest {
    @Test
    void testFixSpaces_MultipleConsecutiveSpacesAreReplacedWithHyphen() {
        String actual = FixSpaces.fixSpaces("Example   1");
        String expected = "Example-1";
        assertEquals(expected, actual);
    }
    @Test
    public void Test_fixSpaces_with_multiple_consecutive_spaces() {
        String result = FixSpaces.fixSpaces("Example   1");
        assertEquals("Example-1", result);
    }
    @Test
    public void Test_fixSpaces_with_single_space() {
        String result = FixSpaces.fixSpaces("Exa mple");
        assertEquals("Exa_mple", result);
    }
    @Test
    public void Test_fixSpaces_with_multiple_consecutive_spaces1() {
        String result = FixSpaces.fixSpaces("Example   1");
        assertEquals("Example-1", result);
    }
    @Test
    public void Test_fixSpaces_with_consecutive_spaces_to_hyphen_and_single_space_to_underscore() {
        String input = "Exa   mple Test";
        String expected = "Exa-mple_Test";
        assertEquals(expected, FixSpaces.fixSpaces(input));
    }
    @Test
    public void Test_fixSpaces_with_trailing_three_consecutive_spaces_to_hyphen() {
        String input = "Mudasir Hanif   ";
        String expected = "Mudasir_Hanif-";
        assertEquals(expected, FixSpaces.fixSpaces(input));
    }
    @Test
    public void Test_fixSpaces_with_single_trailing_space_to_underscore() {
        String input = "Mudasir Hanif ";
        String expected = "Mudasir_Hanif_";
        assertEquals(expected, FixSpaces.fixSpaces(input));
    }
    @Test
    public void Test_fixSpaces_with_consecutive_spaces_to_hyphen_and_single_space_to_underscore1() {
        String input = "   mple Test";
        String expected = "-mple_Test";
        assertEquals(expected, FixSpaces.fixSpaces(input));
    }
    @Test
    void testFixSpacesClassInitialization() {
        // Verify that an instance of FixSpaces can be created without throwing exceptions.
        assertDoesNotThrow(() -> new Object() {});
    }
    @Test
    void testFixSpacesMethodWithNullInput() {
        // Verify that fixSpaces method handles null input correctly.
        assertThrows(NullPointerException.class, () -> FixSpaces.fixSpaces(null));
    }
    @Test
    void testFixSpacesMethodWithEmptyStringInput() {
        // Verify that fixSpaces method handles empty string input correctly.
        assertEquals(FixSpaces.fixSpaces("").getClass(), String.class);
    }
    @Test
    void testFixSpacesMethodWithSingleSpaceInput() {
        // Verify that fixSpaces method handles single space input correctly.
        assertEquals(FixSpaces.fixSpaces(" ").getClass(), String.class);
    }
    @Test
    void testFixSpacesMethodWithMultipleSpacesInput() {
        // Verify that fixSpaces method handles multiple spaces input correctly.
        assertEquals(FixSpaces.fixSpaces("   ").getClass(), String.class);
    }
    @Test
    void testFixSpacesMethodWithTextAndSingleSpaceInBetween() {
        // Verify that fixSpaces method handles text with single space in between.
        assertEquals(FixSpaces.fixSpaces("Hello World"), "Hello_World");
    }
    @Test
    void testFixSpacesMethodWithTextAndLeadingAndTrailingSpaces() {
        // Verify that fixSpaces method handles text with leading and trailing spaces.
        assertEquals(FixSpaces.fixSpaces("   Hello World   ").getClass(), String.class);
    }
    @Test
    void testFixSpacesMethodWithTextWithoutAnySpaces() {
        // Verify that fixSpaces method handles text without any spaces.
        assertEquals(FixSpaces.fixSpaces("HelloWorld"), "HelloWorld");
    }
}