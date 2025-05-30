/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class DoubleTheDifference {
    DoubleTheDifference() {
    }

    public static int doubleTheDifference(List<Object> lst) {
        int sum = 0;
        for (int i = 0; i < lst.size(); ++i) {
            int num;
            if (!(lst.get(i) instanceof Integer) || (num = ((Integer)lst.get(i)).intValue()) % 2 == 0 || num < 0) continue;
            sum += num * num;
        }
        return sum;
    }
}
