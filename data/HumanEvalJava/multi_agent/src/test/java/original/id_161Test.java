package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solve.
*/
class SolveTest {
    @Test
    void testSolve_OnlyLetters_ReversedCase() {
        String result = Solve.solve("ab");
        assertEquals("AB", result);
    }
}