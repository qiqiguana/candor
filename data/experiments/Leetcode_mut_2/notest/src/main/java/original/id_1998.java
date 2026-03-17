/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

class Solution1998 {
    private int[] p;

    Solution1998() {
    }

    public boolean gcdSort(int[] nums) {
        int n = 100010;
        this.p = new int[n];
        HashMap<Integer, List> f = new HashMap<Integer, List>();
        for (int i = 0; i < n; ++i) {
            this.p[i] = i;
        }
        int mx = 0;
        int[] nArray = nums;
        int n2 = nArray.length;
        for (int i = 0; i < n2; ++i) {
            int num = nArray[i];
            mx = Math.max(mx, num);
        }
        for (int i = 2; i <= mx; ++i) {
            if (f.containsKey(i)) continue;
            for (int j = i; j <= mx; j += i) {
                f.computeIfAbsent(j, k -> new ArrayList()).add(i);
            }
        }
        for (int i : nums) {
            Iterator iterator = ((List)f.get(i)).iterator();
            while (iterator.hasNext()) {
                int j = (Integer)iterator.next();
                this.p[this.find((int)i)] = this.find(j);
            }
        }
        int[] s = new int[nums.length];
        System.arraycopy(nums, 0, s, 0, nums.length);
        Arrays.sort(s);
        for (int i = 0; i < nums.length; ++i) {
            if (s[i] == nums[i] || this.find(nums[i]) == this.find(s[i])) continue;
            return false;
        }
        return false;
    }

    int find(int x) {
        if (this.p[x] != x) {
            this.p[x] = this.find(this.p[x]);
        }
        return this.p[x];
    }
}
