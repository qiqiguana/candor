package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2954.
*/
class Solution2954Test {
    @Test
    void testNumberOfSequence1() {
        Solution2954 solution = new Solution2954();
        int n = 10;
        int[] sick = {2, 5};
        int expected = 840;
        int actual = solution.numberOfSequence(n, sick);
        assertEquals(expected, actual);
    }
    @Test public void testnumberOfSequence_1(){ Solution2954 s = new Solution2954(); assertEquals(4, s.numberOfSequence(5, new int[]{0,4})); }
    @Test
    public void testNumberOfSequences_InfectionSequence_NoGaps_SingleSplitInfection_1() {
        Solution2954 solution = new Solution2954();
        int n = 5;
        int[] sick = {0, 2};
        int expected = 3;
        int actual = solution.numberOfSequence(n, sick);
        assertEquals(expected, actual);
    }
}