/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class Search {
    Search() {
    }

    public static int search(List<Integer> lst) {
        int i;
        int max = -1;
        int[] freq = new int[11];
        for (i = 0; i < lst.size(); ++i) {
            int n = lst.get(i);
            freq[n] = freq[n] + 1;
        }
        for (i = 1; i < 11; ++i) {
            if (freq[i] <= i) continue;
            max = i;
        }
        return max;
    }
}
