package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0267.
*/
class Solution0267Test {
    @Test
    void testGeneratePalindromes() {
        // Arrange
        Solution0267 solution = new Solution0267();
        String input = "aab";

        // Act
        List<String> result = solution.generatePalindromes(input);

        // Assert
        assertEquals(1, result.size());
    }
}