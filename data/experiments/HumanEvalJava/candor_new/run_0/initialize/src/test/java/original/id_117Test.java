package original;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SelectWords.
*/
class SelectWordsTest {
    @Test
    void testSelectWordsWithConsonantCountOfFour() {
        String input = "Mary had a little lamb";
        int consonants = 4;
        List<Object> expected = new ArrayList<>(List.of("little"));
        assertEquals(expected, SelectWords.selectWords(input, consonants));
    }
}
