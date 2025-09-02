package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0475.
*/
class Solution0475Test {
    @Test
    void testFindRadius_DistantHeaters_ReturnsMaximumDistance() {
        Solution0475 solution = new Solution0475();
        int[] houses = {1, 2, 3};
        int[] heaters = {10, 20, 30};
        assertEquals(9, solution.findRadius(houses, heaters));
    }
    @Test
    public void testFindRadius(){
        Solution0475 s = new Solution0475();
        int[] houses = {1, 2, 3};
        int[] heaters = {2};
        assertEquals(1, s.findRadius(houses, heaters));
    }
}