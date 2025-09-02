package original;

class Solution0321 {
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
  /**
  You are given two integer arrays nums1 and nums2 of lengths m and n respectively. nums1 and nums2 represent the digits of two numbers. You are also given an integer k. Create the maximum number of length k &lt;= m + n from digits of the two numbers. The relative order of the digits from the same array must be preserved. Return an array of the k digits representing the answer. &nbsp; Example 1: Input: nums1 = [3,4,6,5], nums2 = [9,1,2,5,8,3], k = 5 Output: [9,8,6,5,3] Example 2: Input: nums1 = [6,7], nums2 = [6,0,4], k = 5 Output: [6,7,6,0,4] Example 3: Input: nums1 = [3,9], nums2 = [8,9], k = 3 Output: [9,8,9] &nbsp; Constraints: m == nums1.length n == nums2.length 1 &lt;= m, n &lt;= 500 0 &lt;= nums1[i], nums2[i] &lt;= 9 1 &lt;= k &lt;= m + n nums1 and nums2 do not have leading zeros.
  */
        int m = nums1.length, n = nums2.length;
        int l = Math.max(0, k - n), r = Math.min(k, m);
        int[] ans = new int[k];
        for (int x = l; x <= r; ++x) {
            int[] arr1 = f(nums1, x);
            int[] arr2 = f(nums2, k - x);
            int[] arr = merge(arr1, arr2);
            if (compare(arr, ans, 0, 0)) {
                ans = arr;
            }
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
            if (top + 1 < k) {
                stk[++top] = x;
            } else {
                --remain;
            }
        }
        return stk;
    }

    private int[] merge(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int i = 0, j = 0;
        int[] ans = new int[m + n];
        for (int k = 0; k < m + n; ++k) {
            if (compare(nums1, nums2, i, j)) {
                ans[k] = nums1[i++];
            } else {
                ans[k] = nums2[j++];
            }
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
        return compare(nums1, nums2, i + 1, j + 1);
    }
}