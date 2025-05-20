package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetClosestVowel.
*/
class GetClosestVowelTest {
@Test
void testGetClosestVowel() {
    String word = "yogurt";
    String expected = "u";
    assertEquals(expected, GetClosestVowel.getClosestVowel(word));
}
}