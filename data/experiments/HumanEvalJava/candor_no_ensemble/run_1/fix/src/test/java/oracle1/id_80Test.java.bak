package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsHappy.
*/
class IsHappyTest {
    @Test
    void testIsHappy() {
        String s = "iopaxpoi";
        assertTrue(IsHappy.isHappy(s));
    }
    
    @Test
     void testNothing(){
         IsHappy s = new IsHappy();
         }
    @Test
    public void testEmptyString() {
        assertFalse(IsHappy.isHappy(""));
    }
    @Test
    public void testSingleCharacterString() {
        assertFalse(IsHappy.isHappy("a"));
    }
    @Test
    public void testTwoCharacterString() {
        assertFalse(IsHappy.isHappy("aa"));
    }
    @Test
    public void testThreeCharacterStringWithRepeatedCharacters() {
        assertFalse(IsHappy.isHappy("aaa"));
    }
    @Test
    public void testThreeCharacterStringWithDistinctCharacters() {
        assertTrue(IsHappy.isHappy("abc"));
    }
    @Test
    public void testFourCharacterStringWithRepeatedCharacters() {
        assertFalse(IsHappy.isHappy("aabb"));
    }
    @Test
    public void testFourCharacterStringWithDistinctCharacters() {
        assertTrue(IsHappy.isHappy("abcd"));
    }
    @Test
    public void testLongStringWithDistinctCharacters() {
        assertTrue(IsHappy.isHappy("abcdefghij"));
    }
    @Test
    public void testLongStringWithRepeatedCharactersFixed() {
        String input = "iopaxioi";
        Boolean expectedOutput = false;
        Boolean actualOutput = IsHappy.isHappy(input);
        assertEquals(expectedOutput, actualOutput);
    }
                                  
}