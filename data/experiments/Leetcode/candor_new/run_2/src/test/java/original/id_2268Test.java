package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution2268.
*/
class Solution2268Test {

    @Test
    void test_minimumKeypresses_WhenCalledWithValidInput_ReturnsCorrectResult() {
        // Arrange
        Solution2268 solution = new Solution2268();
        String input = "apple";
        int expectedResult = 5;

        // Act
        int actualResult = solution.minimumKeypresses(input);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
}