package original;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
class Solution3412 {
    public long calculateScore(String s) {
        Map<Character, List<Integer>> d = new HashMap<>(26);
        int n = s.length();
        long ans = 0;
        for (int i = 0; i < n; ++i) {
            char x = s.charAt(i);
            char y = (char) ('a' + 'z' - x);
            if (d.containsKey(y)) {
                var ls = d.get(y);
                int j = ls.remove(ls.size() - 1);
                if (ls.isEmpty()) {
                    d.remove(y);
                }
                ans += i - j;
            } else {
                d.computeIfAbsent(x, k -> new ArrayList<>()).add(i);
            }
        }
        return ans;
    }
}
