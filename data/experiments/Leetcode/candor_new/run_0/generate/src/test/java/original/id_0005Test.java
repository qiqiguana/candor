package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0005.
*/
class Solution0005Test {
    @Test
    void testLongestPalindrome2()
    {
        Solution0005 solution = new Solution0005();
        String actualResult = solution.longestPalindrome("babad");
        String expectedResult = "aba";
        assertEquals(expectedResult,actualResult);
    }
}
