package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ParseNestedParens.
*/
class ParseNestedParensTest {

    @Test
    void testParseNestedParens_SimpleCase() {
        String input = "(()()) ((())) () ((())()())";
        List<Integer> expected = new ArrayList<>(List.of(2, 3, 1, 3));
        assertEquals(expected, ParseNestedParens.parseNestedParens(input));
    }
}