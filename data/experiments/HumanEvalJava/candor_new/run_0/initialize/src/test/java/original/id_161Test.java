package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solve.
*/
class SolveTest {
    @Test
    void testSolve_ReversesStringWhenNoLetters() {
        // Arrange
        String input = "1234";
        String expected = "4321";
        
        // Act
        String actual = Solve.solve(input);
        
        // Assert
        assertEquals(expected, actual);
    }
}