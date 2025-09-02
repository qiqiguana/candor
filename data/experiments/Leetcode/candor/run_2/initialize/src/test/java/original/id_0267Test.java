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
        String s = "aab";
        List<String> expected = new ArrayList<>();
        expected.add("aba");

        // Act
        List<String> actual = solution.generatePalindromes(s);

        // Assert
        assertEquals(expected, actual);
    }
}