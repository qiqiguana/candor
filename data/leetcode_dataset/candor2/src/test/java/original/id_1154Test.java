package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1154.
*/
class Solution1154Test {
    @Test
    void testDayOfYear_LeapYear_February29() {
        // Arrange
        Solution1154 solution = new Solution1154();
        String date = "2020-02-29";
        int expected = 60;
        
        // Act and Assert
        assertEquals(expected, solution.dayOfYear(date));
    }
}