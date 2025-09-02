package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1641.
*/
class Solution1641Test {
    @Test
    void testCountVowelStrings() {
        Solution1641 solution = new Solution1641();
        assertEquals(5, solution.countVowelStrings(1));
    }
    @Test
    public void testCountVowelStrings1() {
        Solution1641 s = new Solution1641();
        assertEquals(5, s.countVowelStrings(1));
    }
    @Test
    public void testCountVowelStrings2() {
        Solution1641 s = new Solution1641();
        assertEquals(15, s.countVowelStrings(2));
    }
    @Test
    public void testCountVowelStrings33() {
        Solution1641 s = new Solution1641();
        assertEquals(66045, s.countVowelStrings(33));
    }
}