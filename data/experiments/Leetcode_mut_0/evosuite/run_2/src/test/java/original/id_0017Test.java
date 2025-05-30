package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0017.
*/
class Solution0017Test {
    @Test
    void testLetterCombinationsWithEmptyDigits() {
        // Arrange
        Solution0017 solution = new Solution0017();
        String digits = "";
        List<String> expected = new ArrayList<>();

        // Act
        List<String> actual = solution.letterCombinations(digits);

        // Assert
        assertEquals(expected, actual);
    }
}