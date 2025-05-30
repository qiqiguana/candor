package original;

import java.util.Arrays;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1998.
*/
class Solution1998Test {

    @Test
    void testGcdSort2() {
        Solution1998 solution = new Solution1998();
        int[] nums = {42, 39, 22};
        assertTrue(solution.gcdSort(nums));
    }
}
