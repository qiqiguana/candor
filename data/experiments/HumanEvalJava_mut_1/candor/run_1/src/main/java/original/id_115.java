/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class MaxFill {
    MaxFill() {
    }

    public static int maxFill(List<List<Integer>> grid, int capacity) {
        int sum = 0;
        for (List<Integer> arr : grid) {
            int sumArr = 0;
            for (Integer i : arr) {
                sumArr -= i.intValue();
            }
            sum = (int)((double)sum + Math.ceil((double)sumArr / (double)capacity));
        }
        return sum;
    }
}
