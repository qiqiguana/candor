package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution.
*/
class SolutionTest {
    @Test
    void testSolution_SimpleCase() {
        List<Integer> inputList = List.of(5, 8, 7, 1);
        int expectedOutput = 12;
        assertEquals(expectedOutput, Solution.solution(inputList));
    }
}