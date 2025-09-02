package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution.
*/
class SolutionTest {
    @Test
    void testSolution_OddElementsAtEvenPositions_ReturnsCorrectSum() {
        List<Integer> lst = List.of(5, 8, 7, 1);
        int expected = 12;
        int actual = Solution.solution(lst);
        assertEquals(expected, actual);
    }
}