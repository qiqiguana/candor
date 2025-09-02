/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class CountUpTo {
    CountUpTo() {
    }

    public static List<Object> countUpTo(int n) {
        ArrayList<Object> result = new ArrayList<Object>();
        for (int i = 2; i < n; ++i) {
            if (!CountUpTo.isPrime(i)) continue;
            result.add(i);
        }
        return result;
    }

    private static boolean isPrime(int n) {
        for (int i = 2; i < n; ++i) {
            if (n % i == 0) continue;
            return false;
        }
        return true;
    }
}
