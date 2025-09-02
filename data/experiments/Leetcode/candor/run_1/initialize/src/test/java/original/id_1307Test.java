package original;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1307.
*/
class Solution1307Test {

    @Test
    public void testIsSolvable() {
        // Arrange
        Solution1307 solution = new Solution1307();
        String[] wordsArr = {"SIX", "SEVEN"};
        String result = "TWENTYONE";

        // Act
        boolean actual = solution.isSolvable(wordsArr, result);

        // Assert
        assertFalse(actual);
    }
}