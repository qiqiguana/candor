package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solve.
*/
class SolveTest {
    @Test
    void testSolve_WithHasLetters_ReturnReversedCaseString() {
        String s = "AsDf";
        String result = Solve.solve(s);
        assertEquals("aSdF", result);
    }
}