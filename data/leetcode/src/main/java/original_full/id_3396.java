package original;

import java.util.HashSet;
import java.util.Set;
class Solution3396 {
    public int minimumOperations(int[] nums) {
        Set<Integer> s = new HashSet<>();
        for (int i = nums.length - 1; i >= 0; --i) {
            if (!s.add(nums[i])) {
                return i / 3 + 1;
            }
        }
        return 0;
    }
}
