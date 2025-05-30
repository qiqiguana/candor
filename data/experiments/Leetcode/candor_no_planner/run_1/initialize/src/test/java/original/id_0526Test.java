package original;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0526.
*/
class Solution0526Test {

    @Test
    void testCountArrangement_WhenNIs4_Returns8() {
        // Arrange
        int n = 4;
        Solution0526 solution = new Solution0526();
        
        // Act
        int actual = solution.countArrangement(n);
        
        // Assert
        assertEquals(8, actual);
    }
}