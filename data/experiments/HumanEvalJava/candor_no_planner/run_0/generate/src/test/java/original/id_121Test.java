package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution.
*/
class SolutionTest {

    @Test
    void testSolution_OddElementsAtEvenPositions() {
        List<Integer> numbers = List.of(3, 13, 2, 9);
        int result = Solution.solution(numbers);
        assertEquals(3, result);
    }
    
    @Test
        public void testNothing(){
            Solution s = new Solution();
            }
                                    
}