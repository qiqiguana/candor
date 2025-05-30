package original;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
class Solution1207 {
    public boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : arr) {
            cnt.merge(x, 1, Integer::sum);
        }
        return new HashSet<>(cnt.values()).size() == cnt.size();
    }
}