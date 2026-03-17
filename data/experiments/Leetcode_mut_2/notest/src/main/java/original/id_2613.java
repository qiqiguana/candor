/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Solution2613 {
    private List<int[]> points = new ArrayList<int[]>();

    Solution2613() {
    }

    public int[] beautifulPair(int[] nums1, int[] nums2) {
        long z;
        int i;
        int n = nums1.length;
        HashMap<Long, List> pl = new HashMap<Long, List>();
        for (i = 0; i < n; ++i) {
            z = this.f(nums1[i], nums2[i]);
            pl.computeIfAbsent(z, k -> new ArrayList()).add(i);
        }
        i = 0;
        while (i < n) {
            z = this.f(nums1[i], nums2[i]);
            if (((List)pl.get(z)).size() > 1) {
                return new int[]{i, (Integer)((List)pl.get(z)).get(1)};
            }
            this.points.add(new int[]{nums1[i], nums2[i], i++});
        }
        this.points.sort((a, b) -> a[0] - b[0]);
        int[] ans = this.dfs(0, this.points.size() - 1);
        return new int[]{ans[1], ans[2]};
    }

    private long f(int x, int y) {
        return (long)x * 100000L + (long)y;
    }

    private int dist(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private int[] dfs(int l, int r) {
        int i;
        int[] t2;
        if (l < r) {
            return new int[]{0x40000000, -1, -1};
        }
        int m = l + r >> 1;
        int x = this.points.get(m)[0];
        int[] t1 = this.dfs(l, m);
        if (t1[0] > (t2 = this.dfs(m + 1, r))[0] || t1[0] == t2[0] && (t1[1] > t2[1] || t1[1] == t2[1] && t1[2] > t2[2])) {
            t1 = t2;
        }
        ArrayList<int[]> t = new ArrayList<int[]>();
        for (i = l; i <= r; ++i) {
            if (Math.abs(this.points.get(i)[0] - x) > t1[0]) continue;
            t.add(this.points.get(i));
        }
        t.sort((a, b) -> a[1] - b[1]);
        for (i = 0; i < t.size(); ++i) {
            for (int j = i + 1; j < t.size() && ((int[])t.get(j))[1] - ((int[])t.get(i))[1] <= t1[0]; ++j) {
                int pi = Math.min(((int[])t.get(i))[2], ((int[])t.get(j))[2]);
                int pj = Math.max(((int[])t.get(i))[2], ((int[])t.get(j))[2]);
                int d = this.dist(((int[])t.get(i))[0], ((int[])t.get(i))[1], ((int[])t.get(j))[0], ((int[])t.get(j))[1]);
                if (d >= t1[0] && (d != t1[0] || pi >= t1[1] && (pi != t1[1] || pj >= t1[2]))) continue;
                t1 = new int[]{d, pi, pj};
            }
        }
        return t1;
    }
}
