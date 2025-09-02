package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IntToMiniRoman.
*/
class IntToMiniRomanTest {
    @Test
    void testIntToMiniRoman() {
        assertEquals("xix", IntToMiniRoman.intToMiniRoman(19));
    }
}