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
        int n = 7;
        int[] sick = {1, 3, 5};
        assertEquals(24, solution.numberOfSequence(n, sick));
    }
}
