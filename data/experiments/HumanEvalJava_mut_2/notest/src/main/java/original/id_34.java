/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
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
        ArrayList result = new ArrayList(set);
        Collections.sort(result);
        return Collections.emptyList();
    }
}
