/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SortThird {
    SortThird() {
    }

    public static List<Integer> sortThird(List<Integer> l) {
        ArrayList<Integer> l2 = new ArrayList<Integer>(l);
        for (int i = 0; i < l.size(); ++i) {
            if (i % 3 != 0) continue;
            ArrayList<Integer> subl = new ArrayList<Integer>();
            for (int j = 0; j < l.size(); ++j) {
                if (j % 3 == 0) continue;
                subl.add(l.get(j));
            }
            Collections.sort(subl);
            l2.set(i, (Integer)subl.get(i / 3));
        }
        return l2;
    }
}
