package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution.
*/
class SolutionTest {

    @Test
    void testSolution_OddNumbersAtEvenPositions() {
        List<Integer> numbers = List.of(5, 8, 7, 1);
        assertEquals(12, Solution.solution(numbers));
    }
}