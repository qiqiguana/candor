package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3398.
*/
class Solution3398Test {
    @Test
    void testMinLength()
    {
        //Arrange
        Solution3398 solution = new Solution3398();
        String s = "11010001";
        int numOps = 2;
        int expected = 1;
        //Act
        int actual = solution.minLength(s, numOps);
        //Assert
        assertEquals(expected,actual);
    }
}