/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class MoveOneBall {
    MoveOneBall() {
    }

    public static Boolean moveOneBall(List<Object> arr) {
        if (arr.size() == 0) {
            return true;
        }
        int count = 0;
        for (int i = 0; i < arr.size(); ++i) {
            if ((Integer)arr.get(i) < (Integer)arr.get((i + 1) % arr.size())) continue;
            ++count;
        }
        if (count <= 1) {
            return true;
        }
        return false;
    }
}
