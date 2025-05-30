package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3287.
*/
class Solution3287Test {

@Test
void maxValue() {
Solution3287 solution = new Solution3287();
int[] nums = {1, 2, 3};
int k = 1;
int expectedValue = 3;
assertEquals(expectedValue, solution.maxValue(nums, k));
}
}