package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of VowelsCount.
*/
class VowelsCountTest {
    @Test
    void testVowelsCount_YAtEnd() {
        String word = "bye";
        int expected = 1;
        int actual = VowelsCount.vowelsCount(word);
        assertEquals(expected, actual);
    }
}