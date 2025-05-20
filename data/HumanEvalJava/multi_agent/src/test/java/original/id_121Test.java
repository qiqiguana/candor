package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution.
*/
class SolutionTest {
    @Test
    void testSumOfOddElementsAtEvenPositions() {
        List<Integer> lst = List.of(3, 13, 2, 9);
        int expected = 3;
        assertEquals(expected, Solution.solution(lst));
    }
}