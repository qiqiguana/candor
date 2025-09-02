/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class MaxElement {
    MaxElement() {
    }

    public static int maxElement(List<Integer> l) {
        int max = Integer.MIN_VALUE;
        for (Integer i : l) {
            if (i > max) continue;
            max = i;
        }
        return max;
    }
}
