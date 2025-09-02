package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solve.
*/
class SolveTest {
    @Test
    void testSolve_ContainsNoLetters_ReversesString() {
        String input = "1234";
        String expectedOutput = "4321";
        String actualOutput = Solve.solve(input);
        assertEquals(expectedOutput, actualOutput);
    }
}
