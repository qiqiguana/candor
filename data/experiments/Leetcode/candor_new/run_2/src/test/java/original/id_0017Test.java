package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0017.
*/
class Solution0017Test {
    @Test
    void testLetterCombinations() {
        Solution0017 solution = new Solution0017();
        List<String> result = solution.letterCombinations("23");
        assertEquals(9, result.size());
    }
}