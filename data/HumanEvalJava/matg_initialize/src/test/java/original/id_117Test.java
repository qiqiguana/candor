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
    void testSelectWordsReturnsEmptyListWhenInputStringIsEmpty() {
        List<Object> result = SelectWords.selectWords("", 4);
        assertTrue(result.isEmpty());
    }
}