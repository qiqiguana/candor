package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FindMax.
*/
class FindMaxTest {
    @Test
    void testFindMax_WhenListOfStringsProvided_ReturnsWordWithMaximumUniqueCharacters() {
        List<String> words = new ArrayList<>();
        words.add("name");
        words.add("of");
        words.add("string");
        String expected = "string";
        assertEquals(expected, FindMax.findMax(words));
    }
}
