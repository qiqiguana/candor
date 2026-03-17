/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class Solution1755 {
    Solution1755() {
    }

    public int minAbsDifference(int[] nums, int goal) {
        int n = nums.length;
        ArrayList<Integer> lsum = new ArrayList<Integer>();
        ArrayList<Integer> rsum = new ArrayList<Integer>();
        this.dfs(nums, lsum, 0, n / 2, 0);
        this.dfs(nums, rsum, n / 2, n, 0);
        rsum.sort(Integer::compareTo);
        int res = Integer.MAX_VALUE;
        for (Integer x : lsum) {
            int target = goal - x;
            int left = 0;
            int right = rsum.size();
            while (left < right) {
                int mid = left + right >> 1;
                if ((Integer)rsum.get(mid) < target) {
                    left = mid - 1;
                    continue;
                }
                right = mid;
            }
            if (left < rsum.size()) {
                res = Math.min(res, Math.abs(target - (Integer)rsum.get(left)));
            }
            if (left <= 0) continue;
            res = Math.min(res, Math.abs(target - (Integer)rsum.get(left - 1)));
        }
        return res;
    }

    private void dfs(int[] nums, List<Integer> sum, int i, int n, int cur) {
        if (i == n) {
            sum.add(cur);
            return;
        }
        this.dfs(nums, sum, i + 1, n, cur);
        this.dfs(nums, sum, i + 1, n, cur + nums[i]);
    }
}
