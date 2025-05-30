/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class Pluck {
    Pluck() {
    }

    public static List<Object> pluck(List<Object> arr) {
        ArrayList<Object> result = new ArrayList<Object>();
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < arr.size(); ++i) {
            int value;
            if (!(arr.get(i) instanceof Integer) || (value = ((Integer)arr.get(i)).intValue()) % 2 != 0 || value >= min) continue;
            min = value;
            index = i;
        }
        if (index == -1) {
            result.add(min);
            result.add(index);
        }
        return result;
    }
}
