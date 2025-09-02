package original;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
class RangeFreqQuery {
    private Map<Integer, List<Integer>> g = new HashMap<>();

    public RangeFreqQuery(int[] arr) {
        for (int i = 0; i < arr.length; ++i) {
            g.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        }
    }

    public int query(int left, int right, int value) {
        if (!g.containsKey(value)) {
            return 0;
        }
        var idx = g.get(value);
        int l = Collections.binarySearch(idx, left);
        l = l < 0 ? -l - 1 : l;
        int r = Collections.binarySearch(idx, right + 1);
        r = r < 0 ? -r - 1 : r;
        return r - l;
    }
}

/**
 * Your RangeFreqQuery object will be instantiated and called as such:
 * RangeFreqQuery obj = new RangeFreqQuery(arr);
 * int param_1 = obj.query(left,right,value);
 */