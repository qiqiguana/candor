package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2954.
*/
class Solution2954Test {
    @Test
    void testNumberOfSequence() {
        Solution2954 solution = new Solution2954();
        int n = 5;
        int[] sick = {1,3};
        assertEquals(6, solution.numberOfSequence(n,sick));
    }
}