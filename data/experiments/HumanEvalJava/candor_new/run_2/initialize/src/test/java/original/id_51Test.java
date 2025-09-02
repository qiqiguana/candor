package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RemoveVowels.
*/
class RemoveVowelsTest {
    @Test
    void testRemoveVowels() {
        String input = "abcdef";
        String expectedOutput = "bcdf";
        assertEquals(expectedOutput, RemoveVowels.removeVowels(input));
    }
}