package original;

import java.util.HashMap;
import java.util.Map;
class Solution2506 {
    public int similarPairs(String[] words) {
        int ans = 0;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (var s : words) {
            int x = 0;
            for (char c : s.toCharArray()) {
                x |= 1 << (c - 'a');
            }
            ans += cnt.merge(x, 1, Integer::sum) - 1;
        }
        return ans;
    }
}
