package original;

import java.util.HashSet;
import java.util.Set;
class Solution2229 {
    public boolean isConsecutive(int[] nums) {
        int mi = nums[0], mx = 0;
        Set<Integer> s = new HashSet<>();
        for (int x : nums) {
            if (!s.add(x)) {
                return false;
            }
            mi = Math.min(mi, x);
            mx = Math.max(mx, x);
        }
        return mx - mi + 1 == nums.length;
    }
}
