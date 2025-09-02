package original;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution.
*/
class SolutionTest {
    @Test
    void testSolution_sumOfOddElementsAtEvenPositions() {
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(8);
        list.add(7);
        list.add(1);
        assertEquals(12, Solution.solution(list));
    }
}
