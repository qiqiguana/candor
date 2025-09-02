package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1641.
*/
class Solution1641Test {
    @Test
    void testCountVowelStrings() {
        Solution1641 solution = new Solution1641();
        int n = 2;
        int expected = 15;
        assertEquals(expected, solution.countVowelStrings(n));
    }
}