package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsHappy.
*/
class IsHappyTest {
    @Test
    void test_isHappy_ReturnsFalseForStringWithLessThan3Characters() {
        // Arrange
        String input = "a";
        Boolean expectedResult = false;

        // Act
        Boolean actualResult = IsHappy.isHappy(input);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
    
    @Test
        public void testNothing(){
            IsHappy s = new IsHappy();
            }
    @Test
    public void Test_Happy_String_with_Distinct_Consecutive_Letters() {
        // Arrange
        String s = "iopaxpoi";
        Boolean result = true;
        // Act
        Boolean actualResult = IsHappy.isHappy(s);
        // Assert
        assertEquals(result, actualResult);
    }
    @Test
    public void Test_Unhappy_String_with_Repeating_Consecutive_Letters() {
        // Arrange
        String s = "xyy";
        Boolean result = false;
        // Act
        Boolean actualResult = IsHappy.isHappy(s);
        // Assert
        assertEquals(result, actualResult);
    }
    @Test
    public void Test_Happy_String_with_Length_3_All_Same() {
        // Arrange
        String s = "aaa";
        Boolean result = false;
        // Act
        Boolean actualResult = IsHappy.isHappy(s);
        // Assert
        assertEquals(false, actualResult);
    }
    @Test
    public void TestConsecutiveLetters() {
    	String input = "aba";
    	Boolean expected = false;
    	Boolean actual = IsHappy.isHappy(input);
    	assertEquals(expected, actual);
    }
                                    
}