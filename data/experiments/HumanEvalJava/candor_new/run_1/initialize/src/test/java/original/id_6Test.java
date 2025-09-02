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
    void test_parseNestedParens_singleGroup() {
        String input = "(()())";
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        assertEquals(expected, ParseNestedParens.parseNestedParens(input));
    }
}