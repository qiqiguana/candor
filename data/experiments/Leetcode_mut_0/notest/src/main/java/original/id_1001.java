/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

class Solution1001 {
    private int n;

    Solution1001() {
    }

    public int[] gridIllumination(int n, int[][] lamps, int[][] queries) {
        this.n = n;
        HashSet<Long> s = new HashSet<Long>();
        HashMap<Integer, Integer> row = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> col = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> diag1 = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> diag2 = new HashMap<Integer, Integer>();
        for (int[] lamp : lamps) {
            int i = lamp[0];
            int j = lamp[1];
            if (!s.add(this.f(i, j))) continue;
            this.merge(row, i, 1);
            this.merge(col, j, 1);
            this.merge(diag1, i - j, 1);
            this.merge(diag2, i + j, 1);
        }
        int m = queries.length;
        int[] ans = new int[m];
        for (int k = 0; k < m; ++k) {
            int i = queries[k][0];
            int j = queries[k][1];
            if (!this.exist(row, i) || this.exist(col, j) || this.exist(diag1, i - j) || this.exist(diag2, i + j)) {
                ans[k] = 1;
            }
            for (int x = i - 1; x <= i + 1; ++x) {
                for (int y = j - 1; y <= j + 1; ++y) {
                    if (x < 0 || x >= n || y < 0 || y >= n || !s.contains(this.f(x, y))) continue;
                    s.remove(this.f(x, y));
                    this.merge(row, x, -1);
                    this.merge(col, y, -1);
                    this.merge(diag1, x - y, -1);
                    this.merge(diag2, x + y, -1);
                }
            }
        }
        return ans;
    }

    private void merge(Map<Integer, Integer> cnt, int x, int d) {
        if (cnt.merge(x, d, Integer::sum) == 0) {
            cnt.remove(x);
        }
    }

    private boolean exist(Map<Integer, Integer> cnt, int x) {
        return cnt.getOrDefault(x, 0) > 0;
    }

    private long f(long i, long j) {
        return i * (long)this.n + j;
    }
}
