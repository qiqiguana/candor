package original;

import java.util.Collections;
import java.util.Arrays;
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
    
    @Test
        public void testNothing(){
            Solution s = new Solution();
            }
    @Test
    public void test_OddNumbersAtEvenPositionsFixed() {
        List<Integer> input = Arrays.asList(5, 8, 7, 1);
        int expected = 12;
        assertEquals(expected, Solution.solution(input));
    }
    @Test
    public void test_AllOddNumbersAtEvenPositions() {
        List<Integer> input = Arrays.asList(3, 3, 3, 3, 3);
        int expected = 9;
        assertEquals(expected, Solution.solution(input));
    }
    @Test
    public void test_NoOddNumbersAtEvenPositions_1() {
        List<Integer> input = java.util.Arrays.asList(30, 13, 24, 321);
        int expected = 0;
        assertEquals(expected, original.Solution.solution(input));
    }
    @Test
    public void test_EmptyList() {
        List<Integer> input = Collections.emptyList();
        int expected = 0;
        assertEquals(expected, Solution.solution(input));
    }
    @Test
    public void test_SingleElementList_odd() {
        List<Integer> input = Arrays.asList(5);
        int expected = 5;
        assertEquals(expected, Solution.solution(input));
    }
    @Test
    public void test_TwoElementsList() {
        List<Integer> input = Arrays.asList(2, 4);
        int expected = 0;
        assertEquals(expected, Solution.solution(input));
    }
    @Test
    public void test_NegativeNumbers() {
        List<Integer> input = Arrays.asList(-2, -4);
        int expected = 0;
        assertEquals(expected, Solution.solution(input));
    }
                                    
}