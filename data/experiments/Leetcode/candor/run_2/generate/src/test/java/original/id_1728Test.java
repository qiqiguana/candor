package original;

import java.util.ArrayDeque;

import java.util.Arrays;

import java.util.ArrayList;

import java.util.Deque;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1728.
*/
class Solution1728Test {
    @Test
    void testCalcFunction() {
        //given
        String[] grid = {"#\\.C","\\..M","\\.#F"};
        int catJump = 2;
        int mouseJump = 1;
        Solution1728 solution = new Solution1728();

        //when
        boolean result = solution.canMouseWin(grid,catJump,mouseJump);

        //then
        assertTrue(result);
    }
}