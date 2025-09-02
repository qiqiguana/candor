package original;

import java.util.Arrays;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1466.
*/
class Solution1466Test {

    @Test
    void testMinReorderShouldReturn2ForGivenConnections() {
        // Arrange
        var sut = new Solution1466();
        int n = 4;
        int[][] connections = {{0,1},{1,3},{2,3}};
        
        // Act
        int result = sut.minReorder(n, connections);
        
        // Assert
        assertEquals(2, result);
    }
}