package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2409.
*/
class Solution2409Test {
    @Test
    void testCountDaysTogether() {
        // Given
        Solution2409 solution = new Solution2409();
        String arriveAlice = "08-15";
        String leaveAlice = "08-18";
        String arriveBob = "08-16";
        String leaveBob = "08-19";

        // When
        int actual = solution.countDaysTogether(arriveAlice, leaveAlice, arriveBob, leaveBob);

        // Then
        assertEquals(3, actual);
    }
}