package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1404.
*/
class Solution1404Test {
    @Test
    void test_numSteps_returnCorrectResult() {
        // Arrange
        Solution1404 solution = new Solution1404();
        String s = "1101";

        // Act
        int result = solution.numSteps(s);

        // Assert
        assertEquals(6, result);
    }
    
    @Test
        public void testNothing(){
            Solution1404 s = new Solution1404();
            }
    @Test
    public void test_Carry_true_at_the_end_of_string_3() {
        Solution1404 solution = new Solution1404();
        int result = solution.numSteps("1110");
        assertEquals(5, result);
    }
    @Test
    void test_Carry_At_End_Of_String() {
        Solution1404 solution = new Solution1404();
        int actualResult = solution.numSteps("111");
        int expectedResult = 4;
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void test_Carry_Flag() {
        Solution1404 solution = new Solution1404();
        int result = solution.numSteps("101");
        assertEquals(5, result);
    }
    @Test
    public void test_Single_Character_Input_Fix_2() {
        Solution1404 solution = new Solution1404();
        int result = solution.numSteps("1");
        assertEquals(0, result);
    }
                                    
}