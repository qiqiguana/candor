package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RemoveVowels.
*/
class RemoveVowelsTest {
    @Test
    void testRemoveVowels() {
        String result = RemoveVowels.removeVowels("abcdef");
        assertEquals("bcdf", result);
    }
}