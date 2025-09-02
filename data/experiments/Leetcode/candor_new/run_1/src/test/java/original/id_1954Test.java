package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1954.
*/
class Solution1954Test {

@Test
void testMinimumPerimeterSmallInput() {
    Solution1954 solution = new Solution1954();
    long neededApples = 1000000000L;
    long expected = 5040;
    assertEquals(expected, solution.minimumPerimeter(neededApples));
}
}