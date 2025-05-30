package original;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1207.
*/
class Solution1207Test {
    @Test
    void testUniqueOccurrences() {
        Solution1207 solution = new Solution1207();
        int[] arr = {1, 2, 2, 1, 1, 3};
        assertTrue(solution.uniqueOccurrences(arr));
    }
}