package original;

import java.util.ArrayDeque;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Deque;

import java.util.Map;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1345.
*/
class Solution1345Test {

    @Test
    void testMinJumps() {
        Solution1345 solution = new Solution1345();
        int[] arr = {100,-23,413,45,219,-49,200,3,-7,212,323,241,-20,-67};
        assertEquals(13, solution.minJumps(arr));
    }
}