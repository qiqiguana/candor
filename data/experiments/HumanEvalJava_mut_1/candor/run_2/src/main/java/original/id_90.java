/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class NextSmallest {
    NextSmallest() {
    }

    public static Integer nextSmallest(List<Object> lst) {
        if (lst.size() <= 1) {
            return null;
        }
        Integer smallest = (Integer)lst.get(0);
        Integer nextSmallest = null;
        for (int i = 1; i < lst.size(); ++i) {
            Integer current = (Integer)lst.get(i);
            if (current <= smallest) {
                nextSmallest = smallest;
                smallest = current;
                continue;
            }
            if (nextSmallest != null && current >= nextSmallest || current == smallest) continue;
            nextSmallest = current;
        }
        return nextSmallest;
    }
}
