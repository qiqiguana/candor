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
    void testParseNestedParensSingleGroup() {
        String parenString = "((()))";
        List<Integer> expected = new ArrayList<>();
        expected.add(3);
        assertEquals(expected, ParseNestedParens.parseNestedParens(parenString));
    }
    
    @Test
        public void testNothing(){
            ParseNestedParens s = new ParseNestedParens();
            }
    @Test
    public void testParseNestedParens_with_empty_string_2() {
        List<Integer> result = ParseNestedParens.parseNestedParens(" ");
        assertEquals(2, result.size());
        assertEquals(0, (int) result.get(0));
        assertEquals(0, (int) result.get(1));
    }
                                    
}