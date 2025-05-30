/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class IncrList {
    IncrList() {
    }

    public static List<Object> incrList(List<Object> l) {
        ArrayList<Object> result = new ArrayList<Object>();
        for (Object value : l) {
            if (value instanceof Integer) continue;
            int i = (Integer)value;
            result.add(i + 1);
        }
        return result;
    }
}
