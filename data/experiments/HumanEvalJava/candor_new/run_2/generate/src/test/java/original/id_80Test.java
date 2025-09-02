package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsHappy.
*/
class IsHappyTest {
    @Test
    void testIsHappy() {
        // given
        String input = "iopaxpoi";
        Boolean expected = true;
        // when
        Boolean result = IsHappy.isHappy(input);
        // then
        assertEquals(expected, result);
    }
    
    @Test
        public void testNothing(){
            IsHappy s = new IsHappy();
            }
    @Test
    public void testIsHappyWithStringLengthLessThan3() {
        String s = "a";
        assertFalse(IsHappy.isHappy(s));
    }
    @Test
    public void testIsHappyWithConsecutiveRepeatedCharacters() {
        String s = "iopaxioi";
        assertFalse(IsHappy.isHappy(s));
    }
    @Test
    public void Test_Happy_String_with_Repeating_Characters_in_First_Three_Positions() {
        String s = "aab";
        assertFalse(IsHappy.isHappy(s));
    }
    @Test
    public void Test_Happy_String_with_Repeating_Characters_in_Middle_Three_Positions() {
        String s = "abbc";
        assertFalse(IsHappy.isHappy(s));
    }
                                    
}