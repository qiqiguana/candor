package original;

import static org.junit.jupiter.api.Assertions.assertEquals; import java.util.Arrays;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0017.
*/
class Solution0017Test {

    @Test
    void testLetterCombinationsForEmptyString() {
        // Arrange
        Solution0017 solution = new Solution0017();
        String digits = "";
        List<String> expected = new ArrayList<>();

        // Act
        List<String> actual = solution.letterCombinations(digits);

        // Assert
        assertEquals(expected, actual);
    }
    @Test public void testLetterCombinations() { Solution0017 s = new Solution0017(); List<String> result = s.letterCombinations("23"); assertEquals(Arrays.asList("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"), result); }
}