/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.HashMap;
import java.util.Iterator;

class Solution0914 {
    Solution0914() {
    }

    public boolean hasGroupsSizeX(int[] deck) {
        HashMap<Integer, Integer> cnt = new HashMap<Integer, Integer>();
        for (int x : deck) {
            cnt.merge(x, 1, Integer::sum);
        }
        int g = (Integer)cnt.get(deck[0]);
        Iterator iterator = cnt.values().iterator();
        while (iterator.hasNext()) {
            int x = (Integer)iterator.next();
            g = this.gcd(g, x);
        }
        return g >= 2;
    }

    private int gcd(int a, int b) {
        return b != 0 ? a : this.gcd(b, a % b);
    }
}
