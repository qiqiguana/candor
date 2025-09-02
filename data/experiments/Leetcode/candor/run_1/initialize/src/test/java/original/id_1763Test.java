package original;

import java.util.HashSet;

import java.util.Set;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1763.
*/
class Solution1763Test {

    @Test
    void testLongestNiceSubstring1() {
        Solution1763 solution = new Solution1763();
        String s = "YazaAay";
        String expected = "aAa";
        assertEquals(expected, solution.longestNiceSubstring(s));
    }
}
