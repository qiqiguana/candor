package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solve.
*/
class SolveTest {
    @Test
    void testSolve_ContainsLetters() {
        String input = "#a@C";
        String expected = "#A@c";
        assertEquals(expected, Solve.solve(input));
    }
}