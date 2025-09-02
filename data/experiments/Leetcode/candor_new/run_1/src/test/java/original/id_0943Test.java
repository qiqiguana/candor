package original;

import java.util.Collections;

import java.util.Arrays;

import java.util.ArrayList;

import java.util.HashSet;

import java.util.List;

import java.util.Set;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0943.
*/
class Solution0943Test {
    @Test
    void testShortestSuperstring() {
        String[] A = {"abc","bcd","cdg"};
        Solution0943 solution0943 = new Solution0943();
        assertEquals("abcdg",solution0943.shortestSuperstring(A));
    }
}