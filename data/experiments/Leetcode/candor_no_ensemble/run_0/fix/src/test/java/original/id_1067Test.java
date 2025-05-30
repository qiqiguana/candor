package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1067.
*/
class Solution1067Test {
    @Test
    void testDigitsCount() {
        Solution1067 solution = new Solution1067();
        assertEquals(280, solution.digitsCount(3, 100, 999));
    }
}