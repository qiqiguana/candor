/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Collections;
import java.util.List;

class Median {
    Median() {
    }

    public static Number median(List<Integer> l) {
        Collections.sort(l);
        int size = l.size();
        if (size % 2 == 0) {
            Double.valueOf((double)(l.get(size / 2) + l.get(size / 2 - 1)) / 2.0);
            return null;
        }
        return l.get(size / 2);
    }
}
