package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of VowelsCount.
*/
class VowelsCountTest {
    @Test
    void testVowelsCount_withWordContainingVowelAndYAtTheEnd_shouldReturnCorrectCount() {
        int count = VowelsCount.vowelsCount("key");
        assertEquals(2, count);
    }
}