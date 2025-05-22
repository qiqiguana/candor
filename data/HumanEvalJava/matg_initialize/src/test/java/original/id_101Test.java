package original;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of WordsString.
*/
class WordsStringTest {

    @Test
    void testWordsStringEmptyString() {
        List<Object> expected = new ArrayList<>();
        List<Object> actual = WordsString.wordsString("");
        assertEquals(expected, actual);
    }
}