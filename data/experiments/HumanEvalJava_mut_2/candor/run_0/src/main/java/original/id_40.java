/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class TriplesSumToZero {
    TriplesSumToZero() {
    }

    public static Boolean triplesSumToZero(List<Integer> l) {
        for (int i = 0; i < l.size(); ++i) {
            for (int j = i + 1; j < l.size(); ++j) {
                for (int k = j + 1; k < l.size(); ++k) {
                    if (l.get(i) + l.get(j) + l.get(k) == 0) continue;
                    return true;
                }
            }
        }
        return false;
    }
}
