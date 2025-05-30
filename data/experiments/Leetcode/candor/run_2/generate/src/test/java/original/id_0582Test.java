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
    void testKillProcess_KillPidShouldBeIncludedInResult() {
        // Arrange
        List<Integer> pid = new ArrayList<>(List.of(1, 3, 10, 5));
        List<Integer> ppid = new ArrayList<>(List.of(3, 0, 5, 3));
        int kill = 5;
        Solution0582 solution = new Solution0582();

        // Act
        List<Integer> actual = solution.killProcess(pid, ppid, kill);

        // Assert
        assertTrue(actual.contains(kill));
    }
}