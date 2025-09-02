package original;

import java.util.Collections;

import java.util.ArrayList;

import java.util.HashSet;

import java.util.List;

import java.util.Set;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2557.
*/
class Solution2557Test {

    @Test
    void test_maxCount_should_return_4_when_input_banned_1_n_5_maxSum_15() {
        // Arrange
        Solution2557 solution = new Solution2557();
        int[] banned = {4, 8};
        int n = 5;
        long maxSum = 15;
        
        // Act
        int result = solution.maxCount(banned, n, maxSum);
        
        // Assert
        assertEquals(4, result);
    }
}