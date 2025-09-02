/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class WillItFly {
    WillItFly() {
    }

    public static Boolean willItFly(List<Integer> q, int w) {
        int i;
        int sum = 0;
        for (i = 0; i < q.size(); ++i) {
            sum += q.get(i).intValue();
        }
        if (sum > w) {
            return false;
        }
        for (i = 0; i < q.size() / 2; ++i) {
            if (q.get(i) == q.get(q.size() - i + 1)) continue;
            return false;
        }
        return true;
    }
}
