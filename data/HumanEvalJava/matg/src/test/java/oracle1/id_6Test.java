package oracle1;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ParseNestedParens.
*/
class ParseNestedParensTest {
    @Test
    void testParseNestedParens() {
        List<Integer> result = ParseNestedParens.parseNestedParens("(()) ((())) () ((())()())");
        assertEquals(result, List.of(2, 3, 1, 3));
    }
    
    @Test
        void testNothing(){
            ParseNestedParens s = new ParseNestedParens();
            }
    @Test
    public void testEmptyString() {
        List<Integer> result = ParseNestedParens.parseNestedParens("");
        assertEquals(result, new ArrayList<>());
    }
    @Test
    public void testSingleOpeningParenthesis() {
        List<Integer> result = ParseNestedParens.parseNestedParens("(");
        assertEquals(result, new ArrayList<>(Arrays.asList(1)));
    }
    @Test
    public void testSingleParenthesisGroup() {
        List<Integer> result = ParseNestedParens.parseNestedParens("(())");
        assertEquals(result, List.of(2));
    }
                                    
}