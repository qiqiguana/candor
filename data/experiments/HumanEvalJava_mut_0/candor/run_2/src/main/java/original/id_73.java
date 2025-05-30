/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class SmallestChange {
    SmallestChange() {
    }

    public static int smallestChange(List<Integer> arr) {
        int result = 0;
        int i = 0;
        for (int j = arr.size() - 1; i < j; ++i, ++j) {
            if (arr.get(i) == arr.get(j)) continue;
            ++result;
        }
        return result;
    }
}
