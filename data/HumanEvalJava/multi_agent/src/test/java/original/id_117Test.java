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
    void testSelectWords_EmptyString_ReturnsEmptyList() {
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, SelectWords.selectWords("", 4));
    }
}
