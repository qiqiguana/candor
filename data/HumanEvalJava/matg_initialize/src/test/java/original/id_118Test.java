package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetClosestVowel.
*/
class GetClosestVowelTest {
    @Test
    void testGetClosestVowel_ReturnsVowel_WhenWordContainsVowelBetweenConsonants() {
        String word = "yogurt";
        String expected = "u";
        String actual = GetClosestVowel.getClosestVowel(word);
        assertEquals(expected, actual);
    }
}