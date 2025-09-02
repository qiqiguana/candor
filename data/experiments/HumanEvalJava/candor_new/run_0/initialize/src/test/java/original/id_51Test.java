package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RemoveVowels.
*/
class RemoveVowelsTest {
    @Test
    void testRemoveVowelsSingleVowel() {
        String input = "a";
        String expectedOutput = "";
        assertEquals(expectedOutput, RemoveVowels.removeVowels(input));
    }
}
