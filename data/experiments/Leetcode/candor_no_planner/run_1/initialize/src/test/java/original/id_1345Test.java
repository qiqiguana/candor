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
    void testMinJumps_CorrectResult() {
        // Arrange
        Solution1345 solution = new Solution1345();
        int[] arr = {100,-23,-23,404,100,23,23,23,3,404};
        int expected = 3;
        
        // Act
        int result = solution.minJumps(arr);
        
        // Assert
        assertEquals(expected, result);
    }
}