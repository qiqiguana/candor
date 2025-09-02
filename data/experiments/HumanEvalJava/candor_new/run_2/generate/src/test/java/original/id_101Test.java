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
    void testWordsString_withCommaSeparatedValues_returnsArrayOfStrings() {
        String input = "Hi, my name is John";
        List<Object> expectedOutput = Arrays.asList("Hi", "my", "name", "is", "John");
        assertEquals(expectedOutput, WordsString.wordsString(input));
    }
}