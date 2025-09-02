package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0267.
*/
class Solution0267Test {
    @Test
    void testGeneratePalindromes() {
        Solution0267 solution = new Solution0267();
        List<String> result = solution.generatePalindromes("aab");
        assertEquals(1, result.size());
    }
}
