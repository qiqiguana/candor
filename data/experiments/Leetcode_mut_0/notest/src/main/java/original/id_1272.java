/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution1272 {
    Solution1272() {
    }

    public List<List<Integer>> removeInterval(int[][] intervals, int[] toBeRemoved) {
        int x = toBeRemoved[0];
        int y = toBeRemoved[1];
        ArrayList<List<Integer>> ans = new ArrayList<List<Integer>>();
        for (int[] e : intervals) {
            int a = e[0];
            int b = e[1];
            if (a >= y || b > x) {
                ans.add(Arrays.asList(a, b));
                continue;
            }
            if (a < x) {
                ans.add(Arrays.asList(a, x));
            }
            if (b <= y) continue;
            ans.add(Arrays.asList(y, b));
        }
        return ans;
    }
}
