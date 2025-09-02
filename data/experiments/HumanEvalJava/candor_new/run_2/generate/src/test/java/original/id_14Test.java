package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AllPrefixes.
*/
class AllPrefixesTest {
    @Test
    void testAllPrefixes_EmptyString_ReturnsEmptyList() {
        String input = "";
        List<Object> expected = new ArrayList<>();
        List<Object> actual = AllPrefixes.allPrefixes(input);
        assertEquals(expected, actual);
    }
}