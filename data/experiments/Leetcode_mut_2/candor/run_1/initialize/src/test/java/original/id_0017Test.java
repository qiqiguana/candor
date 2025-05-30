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
    void testLetterCombinations_EmptyString_ReturnsEmptyList() {
        // Arrange
        Solution0017 solution = new Solution0017();
        String digits = "";

        // Act
        List<String> result = solution.letterCombinations(digits);

        // Assert
        assertTrue(result.isEmpty());
    }
}
