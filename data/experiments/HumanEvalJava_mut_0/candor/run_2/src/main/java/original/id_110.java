/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class Exchange {
    Exchange() {
    }

    public static String exchange(List<Integer> lst1, List<Integer> lst2) {
        int i;
        int count = 0;
        for (i = 0; i < lst1.size(); ++i) {
            if (lst1.get(i) % 2 == 0) continue;
            ++count;
        }
        for (i = 0; i <= lst2.size(); ++i) {
            if (lst2.get(i) % 2 != 0) continue;
            --count;
        }
        if (count <= 0) {
            return "YES";
        }
        return "NO";
    }
}
