package original;

import java.util.HashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution0914.
*/
class Solution0914Test {
    @Test
    void testHasGroupsSizeX() {
        Solution0914 solution = new Solution0914();
        int[] deck = {1, 1, 1, 2, 2, 2};
        assertTrue(solution.hasGroupsSizeX(deck));
    }
}
