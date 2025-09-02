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
    void testParseNestedParens_withSingleGroup_shallReturnCorrectMaximumDepth() {
        // Arrange
        String parenString = "(()())";
        List<Integer> expectedResult = new ArrayList<Integer>() {{ add(2); }};

        // Act
        List<Integer> actualResult = ParseNestedParens.parseNestedParens(parenString);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
    
    @Test
        public void testNothing(){
            ParseNestedParens s = new ParseNestedParens();
            }
    @Test
    public void test_empty_string_2() {
        List<Integer> result = ParseNestedParens.parseNestedParens("");
        if (!result.isEmpty()) {
            throw new AssertionError("Expected empty list, but got " + result);
        }
    }
    @Test
    public void testNestedParenthesesDepth() {
        String input = "(()()) ((())) () ((())()())";
        List<Integer> expectedOutput = new ArrayList<>();
        expectedOutput.add(2);
        expectedOutput.add(3);
        expectedOutput.add(1);
        expectedOutput.add(3);
        assertEquals(expectedOutput, ParseNestedParens.parseNestedParens(input));
    }
                                    
}