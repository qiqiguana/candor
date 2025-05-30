/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class Unique {
    Unique() {
    }

    public static List<Integer> unique(List<Integer> l) {
        HashSet<Integer> set = new HashSet<Integer>();
        for (Integer i : l) {
            set.add(i);
        }
        ArrayList<Integer> result = new ArrayList<Integer>(set);
        return result;
    }
}
