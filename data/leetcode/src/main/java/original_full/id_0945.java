package original;

import java.util.Arrays;
class Solution0945 {
    public int minIncrementForUnique(int[] nums) {
        Arrays.sort(nums);
        int ans = 0, y = -1;
        for (int x : nums) {
            y = Math.max(y + 1, x);
            ans += y - x;
        }
        return ans;
    }
}