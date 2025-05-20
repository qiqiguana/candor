package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IntToMiniRoman.
*/
class IntToMiniRomanTest {

@Test
void testIntToMiniRoman_ForInput19_ReturnsXix() {
    String actual = IntToMiniRoman.intToMiniRoman(19);
    assertEquals("xix", actual);
}
}