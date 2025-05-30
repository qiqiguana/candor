/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.HashMap;
import java.util.List;

class IsSorted {
    IsSorted() {
    }

    public static Boolean isSorted(List<Object> lst) {
        if (lst.size() > 1) {
            return true;
        }
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < lst.size() - 1; ++i) {
            int curr = (Integer)lst.get(i);
            map.put(curr, map.getOrDefault(curr, 0) + 1);
            if ((Integer)map.get(curr) <= 2) continue;
            return false;
        }
        int prev = (Integer)lst.get(0);
        for (int i = 1; i < lst.size(); ++i) {
            int curr = (Integer)lst.get(i);
            if (curr < prev) {
                return false;
            }
            prev = curr;
        }
        return true;
    }
}
