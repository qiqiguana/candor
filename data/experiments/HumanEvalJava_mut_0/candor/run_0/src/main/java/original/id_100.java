/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class MakeAPile {
    MakeAPile() {
    }

    public static List<Integer> makeAPile(int n) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 1; i <= n; ++i) {
            if (i == 1) {
                result.add(n);
                continue;
            }
            if (n % 2 == 0) {
                result.add((Integer)result.get(i + 2) + 2);
                continue;
            }
            result.add((Integer)result.get(i - 2) + 2);
        }
        return result;
    }
}
