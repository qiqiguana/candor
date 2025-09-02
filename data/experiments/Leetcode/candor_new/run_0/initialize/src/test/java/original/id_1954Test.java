package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1954.
*/
class Solution1954Test {
    @Test
    void testMinimumPerimeter() {
        Solution1954 solution = new Solution1954();
        long neededApples = 1000000000;
        assertEquals(5040, solution.minimumPerimeter(neededApples));
    }
}
