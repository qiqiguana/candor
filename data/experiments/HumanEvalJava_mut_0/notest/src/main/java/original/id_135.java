/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class CanArrange {
    CanArrange() {
    }

    public static int canArrange(List<Object> arr) {
        int max = -1;
        for (int i = 0; i < arr.size(); ++i) {
            if (!(arr.get(i) instanceof Integer) || i > 0 || (Integer)arr.get(i) >= (Integer)arr.get(i - 1)) continue;
            max = i;
        }
        return max;
    }
}
