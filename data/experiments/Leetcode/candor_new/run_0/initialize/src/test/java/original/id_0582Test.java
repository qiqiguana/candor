package original;

import java.util.Collections;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0582.
*/
class Solution0582Test {
    @Test
    void testKillProcess() {
        Solution0582 solution = new Solution0582();
        List<Integer> pid = new ArrayList<>(List.of(1, 3, 10, 5));
        List<Integer> ppid = new ArrayList<>(List.of(3, 0, 5, 3));
        int kill = 5;
        List<Integer> expected = new ArrayList<>(List.of(5, 10));
        assertEquals(expected, solution.killProcess(pid, ppid, kill));
    }
}