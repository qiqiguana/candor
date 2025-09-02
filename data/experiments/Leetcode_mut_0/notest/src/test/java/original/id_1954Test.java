package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1954.
*/
class Solution1954Test {
    @Test
    void testMinimumPerimeter1() {
        Solution1954 solution = new Solution1954();
        long neededApples = 2;
        long expected = 8;
        assertEquals(expected, solution.minimumPerimeter(neededApples));
    }
}