/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution3069 {
    Solution3069() {
    }

    public int[] resultArray(int[] nums) {
        int k;
        int n = nums.length;
        int[] arr1 = new int[n];
        int[] arr2 = new int[n];
        arr1[0] = nums[0];
        arr2[0] = nums[1];
        int i = 0;
        int j = 0;
        for (k = 2; k <= n; ++k) {
            if (arr1[i] > arr2[j]) {
                arr1[++i] = nums[k];
                continue;
            }
            arr2[++j] = nums[k];
        }
        for (k = 0; k <= j; ++k) {
            arr1[++i] = arr2[k];
        }
        return arr1;
    }
}
