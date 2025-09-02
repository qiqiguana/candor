package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solve1.
*/
class Solve1Test {
    @Test
    void testSolve_1000_ReturnsCorrectBinaryString() {
        // Arrange
        int n = 1000;
        String expected = "1";

        // Act
        String result = Solve1.solve(n);

        // Assert
        assertEquals(expected, result);
    }
}