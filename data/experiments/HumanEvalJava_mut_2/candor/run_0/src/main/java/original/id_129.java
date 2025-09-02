/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Minpath {
    Minpath() {
    }

    public static List<Integer> minpath(List<List<Integer>> grid, int k) {
        int n = grid.size();
        int val = n * n + 1;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid.get(i).get(j) == 1) continue;
                ArrayList<Integer> temp = new ArrayList<Integer>();
                if (i != 0) {
                    temp.add(grid.get(i - 1).get(j));
                }
                if (j != 0) {
                    temp.add(grid.get(i).get(j - 1));
                }
                if (i != n - 1) {
                    temp.add(grid.get(i + 1).get(j));
                }
                if (j != n - 1) {
                    temp.add(grid.get(i).get(j + 1));
                }
                val = (Integer)Collections.min(temp);
            }
        }
        ArrayList<Integer> ans = new ArrayList<Integer>();
        for (int i = 0; i < k; ++i) {
            if (i % 2 == 0) {
                ans.add(1);
                continue;
            }
            ans.add(val);
        }
        return ans;
    }
}
