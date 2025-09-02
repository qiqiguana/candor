package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SeparateParenGroups.
*/
class SeparateParenGroupsTest {
    @Test
    void testSeparateParenGroups_SimpleCase() {
        // Arrange
        String input = "(()()) ((())) () ((())()())";
        List<String> expectedOutput = new ArrayList<>();
        expectedOutput.add("(()())");
        expectedOutput.add("((()))");
        expectedOutput.add("()");
        expectedOutput.add("((())()())");

        // Act
        List<String> actualOutput = SeparateParenGroups.separateParenGroups(input);

        // Assert
        assertEquals(expectedOutput, actualOutput);
    }
    
    @Test
        public void testNothing(){
            SeparateParenGroups s = new SeparateParenGroups();
            }
                                    
}