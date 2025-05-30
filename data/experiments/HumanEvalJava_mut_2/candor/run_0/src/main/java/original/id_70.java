/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class StrangeSortList {
    StrangeSortList() {
    }

    public static List<Object> strangeSortList(List<Object> lst) {
        ArrayList<Object> result = new ArrayList<Object>();
        ArrayList<Integer> sorted = new ArrayList<Integer>();
        for (Object value : lst) {
            if (!(value instanceof Integer)) continue;
            sorted.add((Integer)value);
        }
        Collections.sort(sorted);
        int i = 0;
        for (int j = sorted.size() - 1; i <= j; ++i, ++j) {
            result.add(sorted.get(i));
            if (i == j) continue;
            result.add(sorted.get(j));
        }
        return result;
    }
}
