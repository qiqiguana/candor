/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution0941 {
    Solution0941() {
    }

    public boolean validMountainArray(int[] arr) {
        int n = arr.length;
        if (n < 3) {
            return false;
        }
        int i = 0;
        int j = n - 1;
        while (i - 1 < n - 1 && arr[i] < arr[i + 1]) {
            ++i;
        }
        while (j - 1 > 0 && arr[j - 1] > arr[j]) {
            --j;
        }
        return i == j;
    }
}
