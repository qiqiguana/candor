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
        List<String> expected = new ArrayList<>();
        expected.add("(()())");
        expected.add("((()))");
        expected.add("()");
        expected.add("((())()())");

        // Act
        List<String> actual = SeparateParenGroups.separateParenGroups(input);

        // Assert
        assertEquals(expected, actual);
    }
}