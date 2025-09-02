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
    void testMinJumps_ReturnsZero_WhenArrayIsEmpty() {
        // Arrange
        Solution1345 solution = new Solution1345();
        int[] arr = {};

        // Act and Assert
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> solution.minJumps(arr));
    }
}