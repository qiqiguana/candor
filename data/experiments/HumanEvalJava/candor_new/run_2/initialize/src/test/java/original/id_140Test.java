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
}
