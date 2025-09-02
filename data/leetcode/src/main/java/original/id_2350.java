package original;

import java.util.HashSet;
import java.util.Set;
class Solution2350 {
    public int shortestSequence(int[] rolls, int k) {
        Set<Integer> s = new HashSet<>();
        int ans = 1;
        for (int v : rolls) {
            s.add(v);
            if (s.size() == k) {
                s.clear();
                ++ans;
            }
        }
        return ans;
    }
}