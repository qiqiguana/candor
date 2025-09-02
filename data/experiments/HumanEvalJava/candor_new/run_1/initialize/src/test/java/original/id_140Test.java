package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FixSpaces.
*/
class FixSpacesTest {
    @Test
    void testFixSpaces_ReplacesConsecutiveSpacesWithUnderscore() {
        String input = "Hello  World";
        String expectedOutput = "Hello__World";
        assertEquals(expectedOutput, FixSpaces.fixSpaces(input));
    }
}