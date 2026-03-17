/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class Monotonic {
    Monotonic() {
    }

    public static Boolean monotonic(List<Integer> l) {
        int size = l.size();
        if (size == 1) {
            return true;
        }
        int direction = l.get(1) - l.get(0);
        for (int i = 1; i < size - 1; ++i) {
            if (direction != 0) {
                direction = l.get(i + 1) - l.get(i);
                continue;
            }
            if (!(direction > 0 ? l.get(i + 1) < l.get(i) : l.get(i + 1) > l.get(i))) continue;
            return false;
        }
        return true;
    }
}
