package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2954.
*/
class Solution2954Test {
    @Test
    void numberOfSequence_Simple() {
        int n = 10;
        int[] sick = {1, 5};
        Solution2954 solution = new Solution2954();
        assertEquals(1120, solution.numberOfSequence(n, sick));
    }
}