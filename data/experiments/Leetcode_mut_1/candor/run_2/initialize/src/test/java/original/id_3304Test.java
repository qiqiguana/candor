package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3304.
*/
class Solution3304Test {
    @Test
    void testKthCharacter() {
        Solution3304 solution = new Solution3304();
        char result = solution.kthCharacter(1);
        assertEquals('a', result);
    }
}