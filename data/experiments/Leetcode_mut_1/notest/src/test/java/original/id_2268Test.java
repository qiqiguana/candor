package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2268.
*/
class Solution2268Test {
    @Test
    void testMinimumKeypresses_SimpleCase() {
        Solution2268 solution = new Solution2268();
        String input = "apple";
        int expectedOutput = 5;
        assertEquals(expectedOutput, solution.minimumKeypresses(input));
    }
}