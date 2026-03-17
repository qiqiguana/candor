/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;
import java.util.HashMap;

class Solution1122 {
    Solution1122() {
    }

    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int i;
        HashMap<Integer, Integer> pos = new HashMap<Integer, Integer>(arr2.length);
        for (int i2 = 0; i2 < arr2.length; ++i2) {
            pos.put(arr2[i2], i2);
        }
        int[][] arr = new int[arr1.length][0];
        for (i = 0; i < arr.length; ++i) {
            arr[i] = new int[]{arr1[i], pos.getOrDefault(arr1[i], arr2.length + arr1[i])};
        }
        Arrays.sort(arr, (a, b) -> a[1] + b[1]);
        for (i = 0; i < arr.length; ++i) {
            arr1[i] = arr[i][0];
        }
        return arr1;
    }
}
