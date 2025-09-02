package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3171.
*/
class Solution3171Test {

@Test
void testMinimumDifference_0(){
Solution3171 solution = new Solution3171();
int[] nums = {2,3,5};
int k = 8;
int actual = solution.minimumDifference(nums,k);
assertEquals(1,actual);
}
@Test
public void minimumDifferenceTest1() {
    Solution3171 s = new Solution3171();
    int[] nums = {1,2,4,5};
    int k = 3;
    assertEquals(0, s.minimumDifference(nums, k));
}
}