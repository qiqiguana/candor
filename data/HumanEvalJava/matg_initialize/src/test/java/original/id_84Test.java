package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solve1.
*/
class Solve1Test {
    @Test
    void testSolve_SumOfDigitsInBinary() {
        int n = 1000;
        String expected = "1";
        String actual = Solve1.solve(n);
        assertEquals(expected, actual);
    }
}