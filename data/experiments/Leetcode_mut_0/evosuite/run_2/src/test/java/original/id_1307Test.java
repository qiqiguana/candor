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
    void testIsSolvable()
    {
        // Arrange
        String[] wordsArr = {"SIX", "SEVEN"};
        String result = "TWENTYONE";
        Solution1307 solution1307 = new Solution1307();

        // Act
        boolean actual = solution1307.isSolvable(wordsArr, result);

        // Assert
        assertFalse(actual);
    }
}