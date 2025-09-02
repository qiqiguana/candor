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
        Solution0943 solution = new Solution0943();
        String[] words = {"catg","ctaagt","gcta","ttca","atgcatc"};
        String expected = "gctaagttcatgcatc";
        assertEquals(expected, solution.shortestSuperstring(words));
    }

}