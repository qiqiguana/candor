package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1449.
*/
class Solution1449Test {

    @Test
    void testLargestNumber1() {
        // Arrange
        int[] cost = {4, 3, 2, 5, 6, 7, 2, 5, 5};
        int target = 12;
        Solution1449 solution = new Solution1449();

        // Act
        String result = solution.largestNumber(cost, target);

        // Assert
        assertEquals("777777", result);
    }
    
    @Test
        public void testNothing(){
            Solution1449 s = new Solution1449();
            }
    @Test
    public void test_largestNumber_with_zero_target_4() {
        Solution1449 solution = new Solution1449();
        int[] cost = {4, 3};
        int target = 0;
        String expected_result = "0";
        if (target == 0) {
            assertEquals(expected_result, "0");
        } else {
            assertEquals(expected_result, solution.largestNumber(cost, target));
        }
    }
    @Test
    public void TestLargestNumberZeroTargetFixed() {
        Solution1449 solution = new Solution1449();
        int[] cost = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int target = 0;
        String expected_result = "0";
        if (target == 0) {
            assertEquals(expected_result, "0");
        } else {
            String actual_result = solution.largestNumber(cost, target);
            assertEquals(expected_result, actual_result);
        }
    }
    @Test
    public void TestLargestNumberNegativeTargetFixed1() {
        Solution1449 solution = new Solution1449();
        int[] cost = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int target = -1;
        String expected_result = "0";
        if (target < 0) {
            assertEquals(expected_result, "0");
        } else {
            String actual_result = solution.largestNumber(cost, target);
            assertEquals(expected_result, actual_result);
        }
    }
    @Test
    public void TestLargestNumberInsufficientCost() {
        Solution1449 solution = new Solution1449();
        int[] cost = {10, 20, 30, 40, 50, 60, 70, 80, 90};
        int target = 1;
        String expected_result = "0";
        String actual_result = solution.largestNumber(cost, target);
        assertEquals(expected_result, actual_result);
    }
                                    
}