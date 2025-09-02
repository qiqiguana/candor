package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution0422.
*/
class Solution0422Test {
    @Test
    void testValidWordSquare_SimpleCase_ReturnsTrue() {
        // Arrange
        Solution0422 solution = new Solution0422();
        List<String> words = List.of("abc", "bca", "cab");
        boolean expectedResult = true;

        // Act
        boolean actualResult = solution.validWordSquare(words);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
}