package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsHappy.
*/
class IsHappyTest {
    @Test
    void isHappy_shortString_returnFalse() {
        // Arrange
        String s = "a";
        Boolean expectedResult = false;

        // Act
        Boolean result = IsHappy.isHappy(s);

        // Assert
        assertEquals(expectedResult, result);
    }
    
    @Test
        public void testNothing(){
            IsHappy s = new IsHappy();
            }
    @Test
    void test_Happy_String_With_Length_Less_Than_3() {
        assertFalse(IsHappy.isHappy("a"));
    }
    @Test
    void test_Unhappy_String_With_Consecutive_Letters() {
        assertFalse(IsHappy.isHappy("aa"));
    }
    @Test
    void test_Happy_String_Without_Consecutive_Letters() {
        assertTrue(IsHappy.isHappy("abcd"));
    }
    @Test
    void test_Unhappy_String_With_Consecutive_Letters_In_Middle() {
        assertFalse(IsHappy.isHappy("aabb"));
    }
    @Test
    void test_Happy_String_Without_Consecutive_Letters_In_Middle() {
        assertTrue(IsHappy.isHappy("adb"));
    }
    @Test
    void test_Unhappy_String_With_Three_Consecutive_Same_Letters() {
        assertFalse(IsHappy.isHappy("xyy"));
    }
    @Test
    void test_Unhappy_String_With_Three_Consecutive_Same_Letters1() {
        assertFalse(IsHappy.isHappy("yyy"));
    }
    @Test
    void test_Happy_String() {
        assertTrue(IsHappy.isHappy("abc"));
    }
    @Test
    public void Test_Happy_String_Length_Less_Than_3() {
        assertFalse(IsHappy.isHappy("a"));
    }
    @Test
    public void Test_Happy_String_Length_Equal_To_3() {
        assertTrue(IsHappy.isHappy("abc"));
    }
    @Test
    public void Test_Happy_String_Repeating_Characters() {
        assertFalse(IsHappy.isHappy("aab"));
    }
    @Test
    public void Test_Happy_String_Distinct_Characters() {
        assertTrue(IsHappy.isHappy("abcd"));
    }
    @Test
    public void Test_Happy_Null_Input_Fixed() {
        try {
            IsHappy.isHappy(null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertTrue(true);
        }
    }
    @Test
    public void IsHappy_EmptyString_ReturnsFalse() {
        assertFalse(IsHappy.isHappy(""));
    }
    @Test
    public void IsHappy_StringLengthLessThan3_ReturnsFalse() {
        assertFalse(IsHappy.isHappy("ab"));
    }
    @Test
    public void IsHappy_StringWithAllDistinctLetters_ReturnsTrue() {
        assertTrue(IsHappy.isHappy("abcd"));
    }
    @Test
    public void IsHappy_StringWithSomeRepeatedLetters_ReturnsFalse() {
        assertFalse(IsHappy.isHappy("aabb"));
    }
    @Test
    public void IsHappy_StringWithConsecutiveRepeats_ReturnsFalse() {
        assertFalse(IsHappy.isHappy("xxxyyy"));
    }
    @Test
    public void IsHappy_LongStringWithDistinctLetters_ReturnsTrue() {
        assertTrue(IsHappy.isHappy("abcdefghi"));
    }
    @Test
    public void IsHappy_NullInput_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> IsHappy.isHappy(null));
    }
                                    
}