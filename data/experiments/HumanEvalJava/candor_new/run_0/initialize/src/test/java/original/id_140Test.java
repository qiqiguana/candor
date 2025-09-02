package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FixSpaces.
*/
class FixSpacesTest {
    @Test
    void testFixSpaces_ReplacesConsecutiveSpacesWithHyphen() {
        String result = FixSpaces.fixSpaces("Exa   mple");
        assertEquals("Exa-mple", result);
    }
}
