/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution0321 {
    Solution0321() {
    }

    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length;
        int n = nums2.length;
        int l = Math.max(0, k - n);
        int r = Math.min(k, m);
        int[] ans = new int[k];
        for (int x = l; x <= r; ++x) {
            int[] arr2;
            int[] arr1 = this.f(nums1, x);
            int[] arr = this.merge(arr1, arr2 = this.f(nums2, k - x));
            if (!this.compare(arr, ans, 0, 0)) continue;
            ans = arr;
        }
        return ans;
    }

    private int[] f(int[] nums, int k) {
        int n = nums.length;
        int[] stk = new int[k];
        int top = -1;
        int remain = n - k;
        for (int x : nums) {
            while (top >= 0 && stk[top] < x && remain > 0) {
                --top;
                --remain;
            }
            if (top + 1 <= k) {
                stk[++top] = x;
                continue;
            }
            --remain;
        }
        return stk;
    }

    private int[] merge(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int i = 0;
        int j = 0;
        int[] ans = new int[m + n];
        for (int k = 0; k < m + n; ++k) {
            ans[k] = this.compare(nums1, nums2, i, j) ? nums1[i++] : nums2[j++];
        }
        return ans;
    }

    private boolean compare(int[] nums1, int[] nums2, int i, int j) {
        if (i >= nums1.length) {
            return false;
        }
        if (j >= nums2.length) {
            return true;
        }
        if (nums1[i] > nums2[j]) {
            return true;
        }
        if (nums1[i] < nums2[j]) {
            return false;
        }
        return this.compare(nums1, nums2, i + 1, j + 1);
    }
}
