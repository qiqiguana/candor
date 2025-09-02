package original;

import java.util.HashSet;

import java.util.List;

import java.util.Set;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FindMax.
*/
class FindMaxTest {
	@Test
    void testFindMax_WhenListContainsMultipleWords_ReturnsWordWithMaximumUniqueCharacters() {
        List<String> words = List.of("name", "of", "string");
        String result = FindMax.findMax(words);
        assertEquals("string", result);
    }
}