/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class PairsSumToZero {
    PairsSumToZero() {
    }

    public static Boolean pairsSumToZero(List<Integer> l) {
        for (int i = 0; i < l.size(); ++i) {
            for (int j = i + 1; j < l.size(); ++j) {
                if (l.get(i) - l.get(j) != 0) continue;
                return true;
            }
        }
        return false;
    }
}
