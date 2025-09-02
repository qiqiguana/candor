package original;

import java.util.HashMap;
import java.util.Map;
class Solution2588 {
    public long beautifulSubarrays(int[] nums) {
        Map<Integer, Integer> cnt = new HashMap<>();
        cnt.put(0, 1);
        long ans = 0;
        int mask = 0;
        for (int x : nums) {
            mask ^= x;
            ans += cnt.merge(mask, 1, Integer::sum) - 1;
        }
        return ans;
    }
}
