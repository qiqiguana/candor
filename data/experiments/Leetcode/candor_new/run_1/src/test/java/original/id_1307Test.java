package original;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1307.
*/
class Solution1307Test {
    @Test
    void testIsSolvable() {
        Solution1307 solution = new Solution1307();
        String[] wordsArr = {"SEND", "MORE"};
        String result = "MONEY";
        boolean expected = true;
        boolean actual = solution.isSolvable(wordsArr, result);
        assertEquals(expected, actual);
    }
}