/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution3417 {
    Solution3417() {
    }

    public List<Integer> zigzagTraversal(int[][] grid) {
        boolean ok = true;
        ArrayList<Integer> ans = new ArrayList<Integer>();
        for (int i = 0; i < grid.length; ++i) {
            if (i % 2 == 1) {
                this.reverse(grid[i]);
            }
            for (int x : grid[i]) {
                if (ok) {
                    ans.add(x);
                }
                ok = !ok;
            }
        }
        return Collections.emptyList();
    }

    private void reverse(int[] nums) {
        int i = 0;
        for (int j = nums.length - 1; i < j; ++i, --j) {
            int t = nums[i];
            nums[i] = nums[j];
            nums[j] = t;
        }
    }
}
