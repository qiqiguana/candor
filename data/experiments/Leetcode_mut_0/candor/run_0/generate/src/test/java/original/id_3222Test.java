package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3222.
*/
class Solution3222Test {
    @Test
    void testLosingPlayer_AliceWins() {
        Solution3222 solution = new Solution3222();
        String result = solution.losingPlayer(10, 20);
        assertEquals("Alice", result);
    }
    
    @Test
        public void testNothing(){
            Solution3222 s = new Solution3222();
            }
    @Test
    void testLosingPlayerWithXGreaterThan0AndYGreaterThanOrEqualTo4Fixed() {
        Solution3222 solution = new Solution3222();
        String result = solution.losingPlayer(1, 8);
        assertEquals("Alice", result);
    }
    @Test
    void testLosingPlayerWithXGreaterThan0AndYLessThan4() {
        Solution3222 solution = new Solution3222();
        String result = solution.losingPlayer(10, 3);
        assertEquals("Bob", result);
    }
    @Test
    void testLosingPlayerWithXLessThanOrEqualTo0() {
        Solution3222 solution = new Solution3222();
        String result = solution.losingPlayer(-5, 8);
        assertEquals("Bob", result);
    }
                                    
}