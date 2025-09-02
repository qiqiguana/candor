package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CountNums.
*/
class CountNumsTest {
	@Test
    void testCountNumsWithSumOfDigitsGreaterThanZero() {
        List<Object> arr = new ArrayList<>();
        arr.add(1);
        arr.add(-2);
        arr.add(3);
        int expected = 2;
        assertEquals(expected, CountNums.countNums(arr));
    }
 
 @Test
     public void testNothing(){
         CountNums s = new CountNums();
         }
 @Test
 public void test_NegativeNumberWithSingleDigit_Fixed_1() {
   List<Object> input = new ArrayList<>();
   input.add(-10);
   assertEquals(0, CountNums.countNums(input));
 }
 @Test
 public void test_NegativeNumberWithSingleDigit_Fixed_2() {
   List<Object> input = new ArrayList<>();
   input.add(-1);
   assertEquals(0, CountNums.countNums(input));
 }
                                 
}