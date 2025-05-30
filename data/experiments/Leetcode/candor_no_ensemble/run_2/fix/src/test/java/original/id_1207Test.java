package original;

import java.util.HashMap;

import java.util.HashSet;

import java.util.Map;

import java.util.Set;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1207.
*/
class Solution1207Test {

    @Test
    void testUniqueOccurrences() {
        Solution1207 solution = new Solution1207();
        int[] arr = {1, 2, 2, 1, 1, 3};
        assertTrue(solution.uniqueOccurrences(arr));
    }
    
    @Test
        public void testNothing(){
            Solution1207 s = new Solution1207();
            }
    @Test
    public void testUniqueOccurrencesWithEmptyArray() {
        Solution1207 solution = new Solution1207();
        int[] arr = {};
        assertTrue(solution.uniqueOccurrences(arr));
    }
    @Test
    public void testUniqueOccurrencesWithSingleElement() {
        Solution1207 solution = new Solution1207();
        int[] arr = {1};
        assertTrue(solution.uniqueOccurrences(arr));
    }
    @Test
    public void testUniqueOccurrencesWithMultipleElementsAndNoDuplicates1() {
        Solution1207 solution = new Solution1207();
        int[] arr = {1, 2, 3};
        assertFalse(solution.uniqueOccurrences(arr));
    }
                                    
}