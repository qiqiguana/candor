package original;

import java.util.Arrays;
class Solution3512 {
    public int minOperations(int[] nums, int k) {
        return Arrays.stream(nums).sum() % k;
    }
}