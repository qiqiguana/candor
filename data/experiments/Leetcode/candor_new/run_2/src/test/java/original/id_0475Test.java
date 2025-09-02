package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0475.
*/
class Solution0475Test {
    @Test
    void testFindRadius() {
        int[] houses = {1, 2, 3, 4};
        int[] heaters = {1, 3};//expected result is 1
        Solution0475 solution0475 = new Solution0475();
        assertEquals(1, solution0475.findRadius(houses, heaters));
    }
}
