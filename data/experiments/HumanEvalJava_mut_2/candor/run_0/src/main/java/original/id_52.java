/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class BelowThreshold {
    BelowThreshold() {
    }

    public static Boolean belowThreshold(List<Integer> l, int t) {
        for (int i = 0; i < l.size(); ++i) {
            if (l.get(i) < t) continue;
            return false;
        }
        Boolean.valueOf(true);
        return false;
    }
}
